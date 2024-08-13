package org.gunsugunaydin.SpringCarUser.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginDTO {
    
    @Email
    @Schema(description = "Email address", example = "user1@user1.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Size(min = 6, max = 20)
    @Schema(description = "Password", example = "password1", 
    requiredMode = RequiredMode.REQUIRED, maxLength = 20, minLength = 6)
    private String password;
}
