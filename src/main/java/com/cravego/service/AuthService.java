package com.cravego.service;

import com.cravego.dto.LoginRequest;
import com.cravego.dto.LoginResponse;
import com.cravego.dto.RegisterRequest;
import com.cravego.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
