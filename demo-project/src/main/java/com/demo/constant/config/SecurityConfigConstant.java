package com.demo.constant.config;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurityConfigConstant {
  // HEADER REQUEST
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  public static final String ACCEPT_HEADER = "Accept";


  // PATTERN REQUEST
  public static final String URL_PATTERN_ALL = "/**";

  // PUBLIC ENDPOINT
  public static final String[] PUBLIC_ENDPOINTS = {
      "/health",
      "/actuator/**",
      "/hello/**"
  };

  // FRONTEND ENDPOINT
  public static final String FRONTEND_ENDPOINT = "http://localhost:4200";

  // METHOD ALLOWED
  public static String GET_METHOD = "GET";
  public static String POST_METHOD = "POST";
  public static String DELETE_METHOD = "DELETE";
  public static String PUT_METHOD = "PUT";
  public static String PATCH_METHOD = "PATCH";
  public static String OPTIONS_METHOD = "OPTIONS";
}
