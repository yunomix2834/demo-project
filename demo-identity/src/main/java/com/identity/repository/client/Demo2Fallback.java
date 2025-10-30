package com.identity.repository.client;

import com.identity.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class Demo2Fallback implements Demo2Client{
    @Override
    public ApiResponse<String> fetchJavaVersion(
            String correlationId) {
        return null;
    }
}
