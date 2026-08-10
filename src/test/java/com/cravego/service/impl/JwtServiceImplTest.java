package com.cravego.service.impl;

import com.cravego.entity.Role;
import com.cravego.entity.User;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private static final String SECRET =
            "TL7XaIb0hHhEZHPn0h7Ri0mSgGrlHKvPMMOrKu9cq1Hk6dn7dID";

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", 86400000L);
    }

    private User buildUser() {
        Role role = Role.builder()
                .id(1L)
                .name("CUSTOMER")
                .build();

        return User.builder()
                .id(1L)
                .email("argey@example.com")
                .role(role)
                .build();
    }

    private UserDetails buildUserDetails(String username) {
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("encoded-password")
                .authorities("ROLE_CUSTOMER")
                .build();
    }

    @Test
    void shouldGenerateTokenAndExtractUsername() {
        User user = buildUser();

        String token = jwtService.generateToken(user);

        assertEquals("argey@example.com", jwtService.extractUsername(token));
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {
        User user = buildUser();
        String token = jwtService.generateToken(user);
        UserDetails userDetails = buildUserDetails("argey@example.com");

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void shouldReturnFalseWhenTokenBelongsToAnotherUser() {
        User user = buildUser();
        String token = jwtService.generateToken(user);
        UserDetails otherUser = buildUserDetails("other@example.com");

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void shouldReturnFalseWhenTokenIsExpired() {
        User user = buildUser();
        ReflectionTestUtils.setField(jwtService, "expiration", -1000L);

        String token = jwtService.generateToken(user);
        UserDetails userDetails = buildUserDetails("argey@example.com");

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void shouldThrowWhenTokenIsInvalid() {
        assertThrows(JwtException.class,
                () -> jwtService.extractUsername("not-a-valid-jwt"));
    }
}
