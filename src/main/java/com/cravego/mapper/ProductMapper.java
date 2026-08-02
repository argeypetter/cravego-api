package com.cravego.mapper;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import com.cravego.entity.Product;

public final class ProductMapper {
    private  ProductMapper() {
    }

    public static Product toEntity(ProductRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setStock(request.getStock());
        product.setAvailable(request.isAvailable());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .available(product.isAvailable())
                .build();
    }

    public static void updateEntity(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setStock(request.getStock());
        product.setAvailable(request.isAvailable());
    }
}
