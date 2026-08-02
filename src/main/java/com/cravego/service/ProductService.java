package com.cravego.service;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> findAll(Pageable pageable);

    ProductResponse findById(Long id);

    ProductResponse save(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}
