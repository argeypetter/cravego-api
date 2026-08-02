package com.cravego.controller;

import com.cravego.dto.CategoryRequest;
import com.cravego.dto.CategoryResponse;
import com.cravego.payload.ApiResponse;
import com.cravego.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "Categories",
        description = "Operations related to category management."
)
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get all categories",
            description = "Returns the complete list of categories."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResponse>>> findAll(
            Pageable pageable) {

        ApiResponse<Page<CategoryResponse>> response =
                ApiResponse.<Page<CategoryResponse>>builder()
                        .success(true)
                        .message("Categories retrieved successfully.")
                        .data(categoryService.findAll(pageable))
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Returns a category by its identifier."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Category found successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(
            @PathVariable Long id) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Category retrieved successfully.")
                        .data(categoryService.findById(id))
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create category",
            description = "Creates a new category."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> save(
            @Valid @RequestBody CategoryRequest request) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Category created successfully.")
                        .data(categoryService.save(request))
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update category",
            description = "Updates an existing category."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Category updated successfully.")
                        .data(categoryService.update(id, request))
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete category",
            description = "Deletes a category."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        categoryService.delete(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Category deleted successfully.")
                        .data(null)
                        .build();

        return ResponseEntity.ok(response);
    }
}