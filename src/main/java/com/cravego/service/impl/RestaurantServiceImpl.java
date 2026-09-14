package com.cravego.service.impl;

import com.cravego.dto.RestaurantRequest;
import com.cravego.dto.RestaurantResponse;
import com.cravego.entity.Restaurant;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.RestaurantMapper;
import com.cravego.repository.RestaurantRepository;
import com.cravego.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Page<RestaurantResponse> findAll(Pageable pageable) {
        return restaurantRepository
                .findAll(pageable)
                .map(RestaurantMapper::toResponse);
    }

    @Override
    public RestaurantResponse findById(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        return RestaurantMapper.toResponse(restaurant);
    }

    @Override
    public RestaurantResponse save(RestaurantRequest restaurantRequest) {
        Restaurant  restaurant = RestaurantMapper.toEntity(restaurantRequest);

        Restaurant save = restaurantRepository.save(restaurant);

        return RestaurantMapper.toResponse(save);
    }

    @Override
    public RestaurantResponse update(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        RestaurantMapper.updateEntity(restaurant, restaurantRequest);

        Restaurant update = restaurantRepository.save(restaurant);

        return RestaurantMapper.toResponse(update);
    }

    @Override
    public void delete(Long id) {

        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }
}
