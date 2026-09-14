package com.cravego.service;

import com.cravego.dto.RestaurantRequest;
import com.cravego.dto.RestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<RestaurantResponse> findAll(Pageable pageable);
    RestaurantResponse findById(Long id);
    RestaurantResponse save(RestaurantRequest restaurantRequest);
    RestaurantResponse update(Long id, RestaurantRequest restaurantRequest);
    void delete(Long id);
}
