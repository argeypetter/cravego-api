package com.cravego.service.impl;

import com.cravego.dto.RestaurantRequest;
import com.cravego.dto.RestaurantResponse;
import com.cravego.entity.Restaurant;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;

    @Test
    void shouldFindRestaurantById() {
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("La Esquina de Casa")
                .description("Bienvenido")
                .address("Calle 42, esquina 9")
                .phone("3225556666")
                .image("test")
                .active(true)
                .build();

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        RestaurantResponse restaurantResponse = restaurantServiceImpl.findById(1L);
        assertNotNull(restaurantResponse);
        assertEquals("La Esquina de Casa", restaurantResponse.getName());
        assertEquals("Calle 42, esquina 9", restaurantResponse.getAddress());
        assertEquals("3225556666", restaurantResponse.getPhone());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
         ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantServiceImpl.findById(1L)
        );

        assertEquals("Restaurant not found", exception.getMessage());
    }

    @Test
    void shouldSaveRestaurant() {
        RestaurantRequest restaurantRequest = RestaurantRequest.builder()
                .name("La Esquina de Casa")
                .description("Bienvenido")
                .address("Calle 42, esquina 9")
                .phone("3225556666")
                .image("test")
                .build();

        Restaurant savedRestaurant = Restaurant.builder()
                .id(1L)
                .name("La Esquina de Casa")
                .description("Bienvenido")
                .address("Calle 42, esquina 9")
                .phone("3225556666")
                .image("test")
                .active(true)
                .build();

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenReturn(savedRestaurant);

        RestaurantResponse restaurantResponse = restaurantServiceImpl.save(restaurantRequest);

        assertNotNull(restaurantResponse);
        assertEquals("La Esquina de Casa", restaurantResponse.getName());
        assertEquals("Bienvenido", restaurantResponse.getDescription());
        assertEquals("Calle 42, esquina 9", restaurantResponse.getAddress());
        assertEquals("3225556666", restaurantResponse.getPhone());
        assertEquals("test", restaurantResponse.getImage());

        verify(restaurantRepository, times(1))
                .save(any(Restaurant.class));
    }

    @Test
    void shouldUpdateRestaurant(){
        // Arrange
        Restaurant existingRestaurant = Restaurant.builder()
                .id(1L)
                .name("La Esquina de Casa")
                .description("Bienvenido")
                .address("Calle 42, esquina 9")
                .phone("3225556666")
                .image("test")
                .active(true)
                .build();

        RestaurantRequest request = new RestaurantRequest();
        request.setName("La Esquina de Casa 2");
        request.setDescription("Disfrusta");
        request.setAddress("Calle 42, esquina 91");
        request.setPhone("3221112222");
        request.setImage("test2");
        request.setActive(true);

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(existingRestaurant));

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        RestaurantResponse response = restaurantServiceImpl.update(1L, request);

        // Assert

        ArgumentCaptor<Restaurant> captor =
                ArgumentCaptor.forClass(Restaurant.class);

        verify(restaurantRepository, times(1))
                .save(captor.capture());

        Restaurant captured = captor.getValue();

        assertEquals("La Esquina de Casa 2", captured.getName());
        assertEquals("Disfrusta", captured.getDescription());
        assertEquals("Calle 42, esquina 91", captured.getAddress());
        assertEquals("3221112222", captured.getPhone());
        assertEquals("test2", captured.getImage());
        assertTrue(captured.isActive());

        assertNotNull(response);
        assertEquals("La Esquina de Casa 2", response.getName());
        assertEquals("Disfrusta", response.getDescription());
        assertEquals("Calle 42, esquina 91", response.getAddress());
        assertEquals("3221112222", response.getPhone());
        assertEquals("test2", response.getImage());
        assertTrue(response.isActive());
    }

    @Test
    void shouldDeleteRestaurant() {
        when(restaurantRepository.existsById(1L))
                .thenReturn(true);
        restaurantServiceImpl.delete(1L);
        verify(restaurantRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingRestaurantThatDoesNotExist() {
        when(restaurantRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantServiceImpl.delete(1L)
        );

        verify(restaurantRepository, never()).deleteById(1L);
    }
}
