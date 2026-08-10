package com.cravego.service.impl;

import com.cravego.dto.LoginRequest;
import com.cravego.dto.LoginResponse;
import com.cravego.dto.RegisterRequest;
import com.cravego.dto.RegisterResponse;
import com.cravego.entity.Role;
import com.cravego.entity.User;
import com.cravego.exception.ResourceAlreadyExistsException;
import com.cravego.repository.RoleRepository;
import com.cravego.repository.UserRepository;
import com.cravego.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest buildRegisterRequest() {
        return RegisterRequest.builder()
                .firstName("Argey")
                .lastName("Sinisterra")
                .email("argey@example.com")
                .password("password123")
                .build();
    }

    private Role buildCustomerRole() {
        return Role.builder()
                .id(1L)
                .name("CUSTOMER")
                .build();
    }

    @Test
    void shouldRegisterUser() {
        RegisterRequest request = buildRegisterRequest();
        Role role = buildCustomerRole();

        User saved = User.builder()
                .id(1L)
                .firstName("Argey")
                .lastName("Sinisterra")
                .email("argey@example.com")
                .password("encoded-password")
                .enabled(true)
                .role(role)
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(roleRepository.findByName("CUSTOMER"))
                .thenReturn(Optional.of(role));
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encoded-password");
        when(userRepository.save(any(User.class)))
                .thenReturn(saved);

        RegisterResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("argey@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = buildRegisterRequest();
        User existing = User.builder()
                .email("argey@example.com")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(existing));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> authService.register(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowWhenCustomerRoleDoesNotExist() {
        RegisterRequest request = buildRegisterRequest();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(roleRepository.findByName("CUSTOMER"))
                .thenReturn(Optional.empty());

        assertThrows(com.cravego.exception.ResourceNotFoundException.class,
                () -> authService.register(request));
    }

    @Test
    void shouldLoginSuccessfully() {
        Role role = buildCustomerRole();
        User user = User.builder()
                .id(1L)
                .email("argey@example.com")
                .password("encoded-password")
                .enabled(true)
                .role(role)
                .build();

        LoginRequest request = LoginRequest.builder()
                .email("argey@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded-password"))
                .thenReturn(true);
        when(jwtService.generateToken(user))
                .thenReturn("fake-jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(1L, response.getUserId());
        assertEquals("argey@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
    }

    @Test
    void shouldThrowWhenEmailDoesNotExist() {
        LoginRequest request = LoginRequest.builder()
                .email("nobody@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request));

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPasswordIsWrong() {
        Role role = buildCustomerRole();
        User user = User.builder()
                .id(1L)
                .email("argey@example.com")
                .password("encoded-password")
                .enabled(true)
                .role(role)
                .build();

        LoginRequest request = LoginRequest.builder()
                .email("argey@example.com")
                .password("wrong-password")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.login(request));

        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void shouldThrowWhenUserIsDisabled() {
        Role role = buildCustomerRole();
        User user = User.builder()
                .id(1L)
                .email("argey@example.com")
                .password("encoded-password")
                .enabled(false)
                .role(role)
                .build();

        LoginRequest request = LoginRequest.builder()
                .email("argey@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded-password"))
                .thenReturn(true);

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request));

        assertTrue(exception.getMessage().contains("disabled"));
        verify(jwtService, never()).generateToken(any());
    }
}
