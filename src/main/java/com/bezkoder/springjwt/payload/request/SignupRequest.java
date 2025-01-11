package com.bezkoder.springjwt.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 10, max = 100, message = "Emaild id must be valid")
    @Email
    private String email;
    private Set<String> role;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 40)
    private String password;
}
