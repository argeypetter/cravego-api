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
public class CategoryRequest {

    @NotBlank(message = "Category name is required.")
    @Size(max = 80, message = "The name cannot exceed 80 characters.")
    private String name;

    @Size(max = 255, message = "The description cannot exceed 255 characters.")
    private String description;

    private boolean active;
}