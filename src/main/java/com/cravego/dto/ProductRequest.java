package com.cravego.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Product Name is require.")
    @Size(max = 80, message = "The name cannot exceed 80 characters.")
    private String name;

    @Size(max = 255, message = "The description cannot exceed 255 characters.")
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank(message = "Product image is required.")
    @Size(max = 255)
    private String image;

    @NotNull
    @PositiveOrZero
    private int stock;

    private boolean available;

    @NotNull
    private Long categoryId;

}
