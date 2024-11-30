package com.innovisor.quickpos.profilemanagement.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull
    @NotBlank
    @Size(min = 11, max = 40)
    @Email(message = "Invalid email format")
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 8, message = "size must be greater than or equal 8")
    private String password;
}
