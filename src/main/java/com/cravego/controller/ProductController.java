package com.cravego.controller;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import com.cravego.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.cravego.payload.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/products")
@Tag(
        name = "Products",
        description = "Operations related to product management."
)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get all products.",
            description = "Returns the complete list of products."
    )

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> findAll(
            @ParameterObject Pageable pageable){

        ApiResponse<Page<ProductResponse>> response =
                ApiResponse.<Page<ProductResponse>>builder()
                        .success(true)
                        .message("Products recovered correctly.")
                        .data(productService.findAll(pageable))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get product by id",
            description = "Returns a product by its identifier."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Product found successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(
            @PathVariable Long id) {

        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product recovered successfully.")
                        .data(productService.findById(id))
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create product",
            description = "Creates a new product."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> save(
            @Valid @RequestBody ProductRequest request){
        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product created successfully.")
                        .data(productService.save(request))
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update product",
            description = "Updates an existing product."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request){
        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product updated successfully.")
                        .data(productService.update(id, request))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete product",
            description = "Deletes a product."
    )

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Product deleted successfully."
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id){

        productService.delete(id);
        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("product deleted successfully.")
                        .data(null)
                        .build();
        return ResponseEntity.ok(response);
    }

}
