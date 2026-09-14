package com.cravego.service.impl;

import com.cravego.dto.UserResponse;
import com.cravego.entity.Role;
import com.cravego.entity.User;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.repository.RoleRepository;
import com.cravego.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void shouldFindUserById()
    {
        Role role = Role.builder().id(1L).name("ADMIN").build();
        User user = User.builder()
                .id(1L)
                .firstName("atom")
                .lastName("Santos")
                .email("atom@cravego.com")
                .role(role)
                .password("password")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        UserResponse response = userService.findById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("atom", response.getFirstName());
        assertEquals("Santos", response.getLastName());
        assertEquals("atom@cravego.com", response.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist()
    {
        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(1L)
        );

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findById(1L)
        );

        assertEquals("User not found", exception.getMessage());
    }



}
