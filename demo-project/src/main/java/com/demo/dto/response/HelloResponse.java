package com.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Response",
        description = "Hello Response Schema"
)
public record HelloResponse (
    @Schema(
            description = "Hello Id in the response"
    )
    String id,

    @Schema(
            description = "UserId in the response"
    )
    String userId,

    @Schema(
            description = "Hello Title in the response"
    )
    String title,

    @Schema(
            description = "Hello Description in the response"
    )
    String description
) {
}