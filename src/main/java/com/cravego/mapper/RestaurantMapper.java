package com.cravego.mapper;

import com.cravego.dto.RestaurantRequest;
import com.cravego.dto.RestaurantResponse;
import com.cravego.entity.Restaurant;

public final class RestaurantMapper {

    private RestaurantMapper() {}

    public static Restaurant toEntity(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setImage(request.getImage());
        restaurant.setActive(request.isActive());

        return restaurant;
    }

    public static RestaurantResponse toResponse(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .image(restaurant.getImage())
                .active(restaurant.isActive())
                .build();
    }

    public static void updateEntity(Restaurant restaurant, RestaurantRequest request) {
        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setImage(request.getImage());
        restaurant.setActive(request.isActive());
    }
}
