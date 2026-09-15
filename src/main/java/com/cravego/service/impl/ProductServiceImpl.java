package com.cravego.service.impl;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import com.cravego.entity.Category;
import com.cravego.entity.Product;
import com.cravego.entity.Restaurant;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.ProductMapper;
import com.cravego.repository.CategoryRepository;
import com.cravego.repository.ProductRepository;
import com.cravego.repository.RestaurantRepository;
import com.cravego.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,  RestaurantRepository restaurantRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable){
        return productRepository
                .findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> findByRestaurantId(Long restaurantId, Pageable pageable) {
        return productRepository.findByRestaurantId(restaurantId, pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse save(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Product product = ProductMapper.toEntity(request);

        product.setCategory(category);

        product.setRestaurant(restaurant);

        Product save = productRepository.save(product);

        return ProductMapper.toResponse(save);
    }

    public ProductResponse update(Long id, ProductRequest productRequest){

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Restaurant restaurant = restaurantRepository.findById(productRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductMapper.updateEntity(product, productRequest);

        product.setCategory(category);

        product.setRestaurant(restaurant);

        Product update = productRepository.save(product);
        return ProductMapper.toResponse(update);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
