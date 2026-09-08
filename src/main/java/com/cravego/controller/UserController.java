package com.cravego.controller;

import com.cravego.dto.UpdateUserRequest;
import com.cravego.dto.UpdateUserStatusRequest;
import com.cravego.dto.UserResponse;
import com.cravego.payload.ApiResponse;
import com.cravego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Users",
        description = "Operations related to user management."
)

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users",
            description = "Returns the complete list of users."
    )

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> findAll(
            Pageable pageable){
        ApiResponse<Page<UserResponse>> response =
                ApiResponse.<Page<UserResponse>>builder()
                        .success(true)
                        .message("Users retrieved successfully.")
                        .data(userService.findAll(pageable))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get users by ID",
            description = "Returns a users by its identifier."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Users found successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Users not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(
            @PathVariable Long id){
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User retrieved successfully.")
                .data(userService.findById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update User",
            description = "Updates an existing User."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request){
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User updated successfully")
                        .data(userService.update(id,request))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update user",
            description = "Updates an existing user."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "user updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "user not found"
            )
    })

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequest request
    ){
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User updated successfully.")
                        .data(userService.updateEnabled(id, request.getEnabled()))
                        .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete User",
            description = "Deletes a User."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        userService.delete(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("User deleted successfully.")
                        .data(null)
                        .build();

        return ResponseEntity.ok(response);
    }
}
