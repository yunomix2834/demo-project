package com.identity.repository.client;

import com.identity.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="demo2", fallback = Demo2Fallback.class)

public interface Demo2Client {

    @GetMapping(
            value = "/api/java-version",
            consumes = "application/json"
    )
    ApiResponse<String> fetchJavaVersion(
        @RequestHeader("yunomix2834-correlation-id") String correlationId);
}
