package com.cravego.controller;


import com.cravego.dto.LoginRequest;
import com.cravego.dto.LoginResponse;
import com.cravego.dto.RegisterRequest;
import com.cravego.dto.RegisterResponse;
import com.cravego.payload.ApiResponse;
import com.cravego.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        ApiResponse<RegisterResponse> response =
                ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .message("Registration completed successfully.")
                        .data(authService.register(request))
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        ApiResponse<LoginResponse> response =
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful.")
                        .data(authService.login(request))
                        .build();

        return ResponseEntity.ok(response);
    }
}
