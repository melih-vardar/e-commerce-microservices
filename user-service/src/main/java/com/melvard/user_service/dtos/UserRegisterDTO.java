package com.melvard.user_service.dtos;

import io.github.melihvardar.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message= "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    private Role role;
}
