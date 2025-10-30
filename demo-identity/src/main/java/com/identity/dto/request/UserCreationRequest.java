package com.identity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "UserCreationRequest",
        description = "Schema user information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {

    @NotEmpty(message = "Username must not be a null or empty")
    @Schema(description = "Username of the User", example = "yunomix2834")
    String username;

    @NotEmpty(message = "Password must not be a null or empty")
    @Schema(description = "Password of the User", example = "yunomix2834@@@")
    String password;
}
