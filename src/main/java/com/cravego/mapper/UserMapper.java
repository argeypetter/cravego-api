package com.cravego.mapper;

import com.cravego.dto.RegisterRequest;
import com.cravego.dto.RegisterResponse;
import com.cravego.dto.UpdateUserRequest;
import com.cravego.dto.UserResponse;
import com.cravego.entity.User;

public final class UserMapper {

    private UserMapper() {
    }


    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return user;
    }


    public static RegisterResponse toResponse(User user) {

        return RegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }


    public static void updateEntity(User user, UpdateUserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
    }

    public static UserResponse toAdminResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .enabled(user.isEnabled())
                .build();
    }
}