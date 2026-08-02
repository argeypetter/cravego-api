package com.cravego.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private int stock;
    private boolean available;
    private Long categoryId;
    private String categoryName;
}
