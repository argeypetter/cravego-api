package com.cravego.service;

import com.cravego.dto.RegisterResponse;
import com.cravego.dto.UpdateUserRequest;
import com.cravego.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponse> findAll(Pageable pageable);
    UserResponse findById(Long id);
    UserResponse update(Long id, UpdateUserRequest request);
    UserResponse updateEnabled(Long id, boolean enabled);
    void delete(Long id);
}
