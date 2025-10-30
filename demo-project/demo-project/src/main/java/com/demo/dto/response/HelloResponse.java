package com.demo.dto.response;

public record HelloResponse (
    String id,
    String userId,
    String title,
    String description
) {
}
