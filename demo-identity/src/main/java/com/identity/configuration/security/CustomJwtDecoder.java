package com.identity.configuration.security;

import com.identity.dto.request.IntrospectRequest;
import com.identity.dto.response.IntrospectResponse;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

/**
 * JwtDecoder tùy chỉnh sử dụng thư viện Nimbus để phân tích và giải mã JWT.
 *
 * <p>Phương thức {@link #decode} sẽ:
 * <ol>
 *   <li>Parse chuỗi token thành đối tượng SignedJWT.</li>
 *   <li>Trích xuất thời gian phát hành (issueTime) và thời gian hết hạn (expirationTime)</li>
 *       dưới dạng Instant.</li>
 *   <li>Tạo đối tượng {@link Jwt} của Spring Security với các thông tin:
 *       token gốc, lần phát hành, thời gian hết hạn, header và claims của JWT.</li>
 *   <li>Ném {@link JwtException} nếu không thể parse hoặc token không hợp lệ.</li>
 * </ol>
 * </p>
 */
@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${app.jwt.signerKey}")
    private String signerKey;

    @Autowired
    private IAuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
      try {
          IntrospectResponse response = authenticationService.introspect(
                  IntrospectRequest.builder()
                          .token(token)
                          .build());

          if (!response.isValid()) {
              throw new AppException(ErrorCode.INVALID_TOKEN);
          }
      } catch (JOSEException | ParseException e) {
          throw new JwtException(e.getMessage());
      }

      if (Objects.isNull(nimbusJwtDecoder)) {
          SecretKeySpec secretKeySpec =
                  new SecretKeySpec(signerKey.getBytes(), "HS512");

          nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                  .macAlgorithm(MacAlgorithm.HS512)
                  .build();
      }

      return nimbusJwtDecoder.decode(token);
    }
}