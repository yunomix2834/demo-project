package com.identity.service.impl;

import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.request.IntrospectRequest;
import com.identity.dto.request.RefreshRequest;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.dto.response.IntrospectResponse;
import com.identity.entity.InvalidatedToken;
import com.identity.entity.Role;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.mapper.UserMapper;
import com.identity.repository.InvalidatedTokenRepository;
import com.identity.repository.RoleRepository;
import com.identity.repository.UserRepository;
import com.identity.service.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl
        implements IAuthenticationService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${app.jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${app.jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${app.jwt.refreshable-duration}")
    protected long REFRESH_DURATION;

    /**
     * Đăng ký người dùng mới chưa kích hoạt và gửi mã OTP.
     *
     * @param request thông tin user để đăng ký
     */
    @Transactional
    @Override
    public void register(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Tạo user nhưng chưa kích hoạt
        User user = userMapper.toUserFromUserCreationRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        roleRepository.findById("USER")
                .ifPresent(r -> user.getRoles().add(r));
        userRepository.save(user);
    }

    /**
     * Đăng nhập bằng username hoặc email, kiểm tra mật khẩu và sinh JWT.
     *
     * @param request thông tin đăng nhập
     * @return AuthenticationResponse chứa accessToken và thông tin user
     * @throws ParseException khi lỗi parse JWT
     */
    @Override
    public AuthenticationResponse login(
            AuthenticationRequest request) throws ParseException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = userRepository
                .findByUsernameOrEmail(request.getUsername(),
                        request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        return generateTokenAndReturnAuthenticationResponse(user);
    }

    /**
     * Đăng xuất, invalid token bằng cách lưu vào repository.
     *
     * @param token chứa token cần logout
     * @throws ParseException khi lỗi parse JWT
     * @throws JOSEException  khi lỗi verify JWT
     */
    @Override
    public void logout(String token)
            throws ParseException, JOSEException {
        try {
            SignedJWT signToken = verifyToken(token, true);

            invalidateToken(signToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    /**
     * Kiểm tra tính hợp lệ của token mà không ném exception.
     *
     * @param request đối tượng chứa token cần kiểm tra
     * @return IntrospectResponse với flag valid
     * @throws JOSEException  khi lỗi ký JWT
     * @throws ParseException khi lỗi parse JWT
     */
    @Override
    public IntrospectResponse introspect(
            IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = true;
        String userId = null;

        try {
            SignedJWT signedJWT = verifyToken(token, false);

            userId = signedJWT.getJWTClaimsSet().getStringClaim("userId");
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .userId(userId)
                .build();
    }

    /**
     * Làm mới access token từ refresh token.
     *
     * @param request chứa token cũ
     * @return AuthenticationResponse chứa token mới và expiryTime
     * @throws ParseException khi lỗi parse JWT
     * @throws JOSEException  khi lỗi verify JWT
     */
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        invalidateToken(signedJWT);

        User user = userRepository
                .findByEmail(signedJWT.getJWTClaimsSet().getSubject())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return generateTokenAndReturnAuthenticationResponse(user);
    }

    private SignedJWT generateToken(
            User user,
            long duration,
            String type) {
        JWSHeader header = new JWSHeader
                .Builder(JWSAlgorithm.HS512)
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet jwtClaimsSet = buildJwtClaimsSet(user, duration, type);

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);

        try {
            JWSSigner signer =
                    new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8));
            signedJWT.sign(signer);

            return signedJWT;
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.FAILED_GENERATE_TOKEN);
        }
    }

    private JWTClaimsSet buildJwtClaimsSet(
            User user,
            long duration,
            String type) {
        Instant expiryInstant = Instant.now()
                .plus(duration, ChronoUnit.SECONDS);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Code Campus")
                .issueTime(new Date())
                .expirationTime(Date.from(expiryInstant))
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId())
                .claim("roles", user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList()
                )
                .claim("token_type", type);

        return builder.build();
    }

    private SignedJWT verifyToken(
            String token,
            boolean isRefresh)
            throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = isRefresh
                ? Date.from(signedJWT.getJWTClaimsSet()
                .getIssueTime()       // thời điểm phát hành
                .toInstant()
                .plus(REFRESH_DURATION, ChronoUnit.SECONDS))
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(
                new MACVerifier(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(
                signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.TOKEN_REVOKED);
        }

        return signedJWT;
    }

    private void invalidateToken(SignedJWT signedJWT)
            throws ParseException {
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime.toInstant())
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private AuthenticationResponse generateTokenAndReturnAuthenticationResponse(
            User user)
            throws ParseException {
        SignedJWT accessToken =
                generateToken(user, VALID_DURATION, "access_token");
        SignedJWT refreshToken =
                generateToken(user, REFRESH_DURATION, "refresh_token");

        return AuthenticationResponse.builder()
                .accessToken(accessToken.serialize())
                .refreshToken(refreshToken.serialize())
                .accessExpiry(accessToken.getJWTClaimsSet().getExpirationTime()
                        .toInstant())
                .refreshExpiry(
                        refreshToken.getJWTClaimsSet().getExpirationTime()
                                .toInstant())
                .authenticated(true)
                .build();
    }
}
