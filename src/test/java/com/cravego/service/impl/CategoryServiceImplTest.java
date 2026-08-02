package com.cravego.service.impl;

import com.cravego.dto.CategoryRequest;
import com.cravego.dto.CategoryResponse;
import com.cravego.entity.Category;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void shouldFindCategoryById() {

        Category category = Category.builder()
                .id(1L)
                .name("Hamburguesas")
                .description("Comida rápida")
                .active(true)
                .build();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Hamburguesas", response.getName());
        assertEquals("Comida rápida", response.getDescription());

    }

    @Test
    void shouldThrowExceptionWhenCategoryDoesNotExist() {
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.findById(1L)
        );

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.findById(1L)
        );

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void shouldSaveCategory() {

        CategoryRequest request = CategoryRequest.builder()
                .name("Pizzas")
                .description("Pizza italiana")
                .build();

        Category savedCategory = Category.builder()
                .id(1L)
                .name("Pizzas")
                .description("Pizza italiana")
                .active(true)
                .build();

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(savedCategory);

        CategoryResponse response = categoryService.save(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Pizzas", response.getName());
        assertEquals("Pizza italiana", response.getDescription());

        verify(categoryRepository, times(1))
                .save(any(Category.class));
    }

    @Test
    void shouldUpdateCategory() {

        // Arrange

        Category existingCategory = Category.builder()
                .id(1L)
                .name("Hamburguesas")
                .description("Normal")
                .active(true)
                .build();

        CategoryRequest request = new CategoryRequest();
        request.setName("Hamburguesa Premium");
        request.setDescription("Doble carne");
        request.setActive(true);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existingCategory));

        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        CategoryResponse response = categoryService.update(1L, request);

        // Assert

        ArgumentCaptor<Category> captor =
                ArgumentCaptor.forClass(Category.class);

        verify(categoryRepository, times(1))
                .save(captor.capture());

        Category captured = captor.getValue();

        assertEquals("Hamburguesa Premium", captured.getName());
        assertEquals("Doble carne", captured.getDescription());
        assertTrue(captured.isActive());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Hamburguesa Premium", response.getName());
        assertEquals("Doble carne", response.getDescription());
        assertTrue(response.isActive());
    }

    @Test
    void shouldDeleteCategory() {
        when(categoryRepository.existsById(1L))
                .thenReturn(true);

        categoryService.delete(1L);

        verify(categoryRepository, times(1))
                .deleteById(1L);

    }

    @Test
    void shouldThrowExceptionWhenDeletingCategoryThatDoesNotExist() {
        when(categoryRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.delete(1L)
        );
        verify(categoryRepository, never())
                .deleteById(anyLong());
    }
}