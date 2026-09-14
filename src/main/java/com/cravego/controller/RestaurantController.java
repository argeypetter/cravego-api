package com.cravego.controller;

import com.cravego.dto.RestaurantRequest;
import com.cravego.dto.RestaurantResponse;
import com.cravego.service.RestaurantService;
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
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurants",
        description = "Operations related to restaurant management."
)
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
            summary = "Get all restaurants.",
            description = "Returns the complete list of restaurants"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<RestaurantResponse>>> findAll(
            @ParameterObject Pageable pageable) {
        ApiResponse<Page<RestaurantResponse>> response =
                ApiResponse.<Page<RestaurantResponse>>builder()
                        .success(true)
                        .message("Restaurants recovered correctly.")
                        .data(restaurantService.findAll(pageable))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get restaurant by id",
            description = "Returns a restaurant by its identifier."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Restaurant found successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Restaurant not found"
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> findById(
            @PathVariable Long id){

        ApiResponse<RestaurantResponse> response =
                ApiResponse.<RestaurantResponse>builder()
                        .success(true)
                        .message("Restaurant recovered successfully.")
                        .data(restaurantService.findById(id))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create restaurant",
            description = "Creates a new restaurant"
    )

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Restaurant created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Restaurant not found"
            )
    })

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantResponse>> save(
            @Valid @RequestBody RestaurantRequest request){
        ApiResponse<RestaurantResponse> response =
                ApiResponse.<RestaurantResponse>builder()
                        .success(true)
                        .message("Restaurant created successfully.")
                        .data(restaurantService.save(request))
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update restaurant",
            description = "Updates an existing restaurant."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "restaurant updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "restaurant not found"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> update(
            @Valid @RequestBody RestaurantRequest request,
            @PathVariable Long id
    ){
        ApiResponse<RestaurantResponse> response =
                ApiResponse.<RestaurantResponse>builder()
                        .success(true)
                        .message("Restaurant updated successfully.")
                        .data(restaurantService.update(id, request))
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id){
        restaurantService.delete(id);
        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("restaurant deleted successfully.")
                        .data(null)
                        .build();
        return ResponseEntity.ok(response);
    }
}
