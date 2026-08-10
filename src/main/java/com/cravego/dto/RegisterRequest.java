package com.cravego.dto;

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
public class RegisterRequest {
    @NotBlank(message = "FirstName is required.")
    @Size(max = 80, message = "The name cannot exceed 80 characters.")
    private String firstName;
    @NotBlank(message = "LastName is required.")
    @Size(max = 80, message = "The name cannot exceed 80 characters.")
    private String lastName;
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Size(max = 100, message = "The email cannot exceed 100 characters.")
    private String email;
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 100,
            message = "Password must be between 8 and 100 characters.")
    private String password;


}
