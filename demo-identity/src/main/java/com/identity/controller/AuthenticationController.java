package com.identity.controller;

import com.identity.dto.ApiResponse;
import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.request.IntrospectRequest;
import com.identity.dto.request.RefreshRequest;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.dto.response.IntrospectResponse;
import com.identity.helper.AuthenticationHelper;
import com.identity.service.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {
  IAuthenticationService authenticationService;

  @PostMapping("/login")
  ApiResponse<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest request)
      throws ParseException {
    return ApiResponse.<AuthenticationResponse>builder()
        .result(authenticationService.login(request))
        .message("Login successful")
        .build();
  }

  @PostMapping("/register")
  ApiResponse<Void> register(
      @RequestBody UserCreationRequest request) {
    authenticationService.register(request);
    return ApiResponse.<Void>builder()
        .code(201)
        .message("Register successful.")
        .build();
  }

  @PostMapping("/introspect")
  ApiResponse<IntrospectResponse> introspect(
      @RequestBody IntrospectRequest request)
      throws ParseException, JOSEException {
    return ApiResponse.<IntrospectResponse>builder()
        .result(authenticationService.introspect(request))
        .message("Introspection successful")
        .build();
  }

  @PostMapping("/refresh")
  ApiResponse<AuthenticationResponse> refreshToken(
      @RequestBody RefreshRequest request)
      throws ParseException, JOSEException {
    return ApiResponse.<AuthenticationResponse>builder()
        .result(authenticationService.refreshToken(request))
        .message("Refresh successful")
        .build();
  }

  @PostMapping("/logout")
  ApiResponse<Void> logout(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
      throws ParseException, JOSEException {

    authenticationService
        .logout(AuthenticationHelper.extractToken(authHeader));

    return ApiResponse.<Void>builder()
        .code(204)
        .message("Logout successful")
        .build();
  }
}
