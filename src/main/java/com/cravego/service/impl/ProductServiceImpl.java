package com.cravego.service.impl;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import com.cravego.entity.Product;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.ProductMapper;
import com.cravego.repository.ProductRepository;
import com.cravego.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable){
        return productRepository
                .findAll(pageable)
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
        Product product = ProductMapper.toEntity(request);

        Product save = productRepository.save(product);

        return ProductMapper.toResponse(save);
    }

    public ProductResponse update(Long id, ProductRequest productRequest){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductMapper.updateEntity(product, productRequest);

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
