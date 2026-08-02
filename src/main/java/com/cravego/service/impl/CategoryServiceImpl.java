package com.cravego.service.impl;

import com.cravego.dto.CategoryRequest;
import com.cravego.dto.CategoryResponse;
import com.cravego.entity.Category;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.mapper.CategoryMapper;
import com.cravego.repository.CategoryRepository;
import com.cravego.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {

        return categoryRepository
                .findAll(pageable)
                .map(CategoryMapper::toResponse);

    }

    @Override
    public CategoryResponse findById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return CategoryMapper.toResponse(category);
    }


    @Override
    public CategoryResponse save(CategoryRequest request) {

        Category category = CategoryMapper.toEntity(request);

        Category save = categoryRepository.save(category);

        return CategoryMapper.toResponse(save);
    }


    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        CategoryMapper.updateEntity(category, request);

        Category updated = categoryRepository.save(category);

        return CategoryMapper.toResponse(updated);
    }


    @Override
    public void delete(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }

        categoryRepository.deleteById(id);
    }
}