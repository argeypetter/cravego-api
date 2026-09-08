package com.cravego.dto;

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
public class UpdateUserRequest {
    @NotBlank(message = "FirstName is required.")
    @Size(max = 30, message = "The name cannot exceed 30 characters.")
    private String firstName;

    @NotBlank(message = "LastName is required.")
    @Size(max = 30, message = "The name cannot exceed 30 characters.")
    private String lastName;

    @NotBlank(message = "Role is required.")
    private String role;
}
