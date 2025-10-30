package com.identity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String tokenAccessType;
    String accessToken;
    String refreshToken;
    Instant accessExpiry;
    Instant refreshExpiry;
    boolean authenticated;
}
