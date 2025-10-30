package com.identity.service;

import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.request.IntrospectRequest;
import com.identity.dto.request.RefreshRequest;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {

    /**
     * Đăng ký người dùng mới chưa kích hoạt và gửi mã OTP.
     *
     * @param request thông tin user để đăng ký
     */
    void register(UserCreationRequest request);

    /**
     * Đăng nhập bằng username hoặc email, kiểm tra mật khẩu và sinh JWT.
     *
     * @param request thông tin đăng nhập
     * @return AuthenticationResponse chứa accessToken và thông tin user
     * @throws ParseException khi lỗi parse JWT
     */
    AuthenticationResponse login(AuthenticationRequest request)
            throws ParseException;

    /**
     * Đăng xuất, invalid token bằng cách lưu vào repository.
     *
     * @param token chứa token cần logout
     * @throws ParseException khi lỗi parse JWT
     * @throws JOSEException  khi lỗi verify JWT
     */
    void logout(String token)
            throws ParseException, JOSEException;

    /**
     * Kiểm tra tính hợp lệ của token mà không ném exception.
     *
     * @param request đối tượng chứa token cần kiểm tra
     * @return IntrospectResponse với flag valid
     * @throws JOSEException  khi lỗi ký JWT
     * @throws ParseException khi lỗi parse JWT
     */
    IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException;

    /**
     * Làm mới access token từ refresh token.
     *
     * @param request chứa token cũ
     * @return AuthenticationResponse chứa token mới và expiryTime
     * @throws ParseException khi lỗi parse JWT
     * @throws JOSEException  khi lỗi verify JWT
     */
    AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException;
}
