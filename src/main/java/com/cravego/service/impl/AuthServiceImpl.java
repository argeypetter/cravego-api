package com.cravego.service.impl;

import com.cravego.dto.LoginRequest;
import com.cravego.dto.LoginResponse;
import com.cravego.dto.RegisterRequest;
import com.cravego.dto.RegisterResponse;
import com.cravego.entity.Role;
import com.cravego.entity.User;
import com.cravego.exception.ResourceAlreadyExistsException;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.UserMapper;
import com.cravego.repository.RoleRepository;
import com.cravego.repository.UserRepository;
import com.cravego.service.AuthService;
import com.cravego.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService  jwtService;
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,  JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Override
    public RegisterResponse register(RegisterRequest request) {

        // 1. Validar que el correo no exista
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Email is already registered"
            );
        }

        // 2. Buscar rol CUSTOMER
        Role role = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found")
                );

        // 3. Convertir Request -> Entity
        User user = UserMapper.toEntity(request);

        // 4. Cifrar contraseña
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // 5. Configurar datos del usuario
        user.setRole(role);
        user.setEnabled(true);

        // 6. Guardar usuario
        User saved = userRepository.save(user);

        // 7. Entity -> Response
        return UserMapper.toResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadCredentialsException("Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new BadCredentialsException("User account is disabled");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}
