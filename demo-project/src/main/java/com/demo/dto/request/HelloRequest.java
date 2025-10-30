package com.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "HelloRequest",
        description = "Schema hello information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloRequest {

    @Schema(description = "UserID of the Hello", example = "123456789abcd")
    String userId;

    @NotEmpty(message = "Title must not be a null or empty")
    @Schema(description = "Title of the Hello", example = "HelloTitle")
    String title;

    @NotEmpty(message = "Description mus not be a null or empty")
    @Schema(description = "Description of the Hello", example = "HelloDescription")
    String description;
}
