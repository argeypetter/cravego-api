package com.cravego.service.impl;

import com.cravego.dto.UpdateUserRequest;
import com.cravego.dto.UserResponse;
import com.cravego.entity.Role;
import com.cravego.entity.User;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.UserMapper;
import com.cravego.repository.RoleRepository;
import com.cravego.repository.UserRepository;
import com.cravego.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toAdminResponse);
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.toAdminResponse(user);

    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserMapper.updateEntity(user, request);

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role);

        User updated = userRepository.save(user);

        return UserMapper.toAdminResponse(updated);

    }

    @Override
    public UserResponse updateEnabled(Long id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String currentEmail = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        if (user.getEmail().equals(currentEmail)) {
            throw new IllegalStateException("You cannot disable your own account");
        }

        user.setEnabled(enabled);
        User updated = userRepository.save(user);

        return UserMapper.toAdminResponse(updated);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String currentEmail = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        if (user.getEmail().equals(currentEmail)) {
            throw new IllegalStateException("You cannot delete your own account");
        }

        userRepository.deleteById(id);
    }
}
