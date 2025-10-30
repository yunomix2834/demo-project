package com.identity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Response",
        description = "User Response Schema"
)
public record UserResponse(
    @Schema(
            description = "UserId in the response"
    )
    String id,
    @Schema(
            description = "Username in the response"
    )
    String username
) {
}
