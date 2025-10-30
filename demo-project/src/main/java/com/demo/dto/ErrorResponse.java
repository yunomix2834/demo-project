package com.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    @Schema(
            description = "Error code representing the error happened"
    )
    int code;

    @Schema(
            description = "API path invoked by client"
    )
    String apiPath;

    @Schema(
            description = "Error message representing the error happened"
    )
    String message;

    String status;

    @Schema(
            description = "Time representing when the error happened"
    )
    LocalDateTime errorTime;
}
