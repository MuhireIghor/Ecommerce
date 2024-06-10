package com.ne.template.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SignInDto {
    @NotBlank(message = "email is required")
    @Email(message = "Email must be a valid email")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
