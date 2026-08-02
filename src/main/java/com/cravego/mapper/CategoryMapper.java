package com.cravego.mapper;

import com.cravego.dto.CategoryRequest;
import com.cravego.dto.CategoryResponse;
import com.cravego.entity.Category;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setActive(request.isActive());

        return category;
    }

    public static CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.isActive())
                .build();
    }

    public static void updateEntity(Category category, CategoryRequest request) {

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setActive(request.isActive());

    }
}