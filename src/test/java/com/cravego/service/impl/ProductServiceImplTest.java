package com.cravego.service.impl;

import com.cravego.dto.ProductRequest;
import com.cravego.dto.ProductResponse;
import com.cravego.entity.Category;
import com.cravego.entity.Product;
import com.cravego.exception.ResourceNotFoundException;
import com.cravego.repository.CategoryRepository;
import com.cravego.repository.ProductRepository;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void shouldFindProductById() {
        Category category = Category.builder()
                .id(1L)
                .name("Comidas Rápidas")
                .description("Categoría de comidas rápidas")
                .active(true)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Papas francesa")
                .description("Papas, mayonesa")
                .price(new BigDecimal("25000"))
                .stock(2)
                .available(true)
                .category(category)
                .build();
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response = productServiceImpl.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Papas francesa", response.getName());
        assertEquals("Papas, mayonesa", response.getDescription());
        assertEquals(new BigDecimal("25000"), response.getPrice());
        assertEquals(2, response.getStock());

        verify(productRepository, times(1))
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productServiceImpl.findById(1L)
        );
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void shouldSaveProduct(){
        Category category = Category.builder()
                .id(1L)
                .name("Comidas Rápidas")
                .description("Categoría de comidas rápidas")
                .active(true)
                .build();

        ProductRequest request = ProductRequest.builder()
                .name("Pizzas")
                .description("Pizza intaliana")
                .price(new BigDecimal("50000"))
                .stock(23)
                .available(true)
                .categoryId(category.getId())
                .build();

        Product saveProduct = Product.builder()
                .id(1L)
                .name("Pizzas")
                .description("Pizza intaliana")
                .price(new BigDecimal("50000"))
                .stock(23)
                .available(true)
                .category(category)
                .build();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(productRepository.save(any(Product.class)))
                .thenReturn(saveProduct);
        ProductResponse response = productServiceImpl.save(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Pizzas", response.getName());
        assertEquals("Pizza intaliana", response.getDescription());
        assertEquals(new BigDecimal("50000"), response.getPrice());
        assertEquals(23, response.getStock());

        verify(categoryRepository).findById(1L);
        verify(productRepository).save(any(Product.class));

        ArgumentCaptor<Product> captor =
                ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(captor.capture());

        Product captured = captor.getValue();

        assertEquals(category.getId(), captured.getCategory().getId());
        assertEquals("Comidas Rápidas", captured.getCategory().getName());
    }

    @Test
    void shouldUpdateProduct(){
        Category existingCategory = Category.builder()
                .id(1L)
                .name("Hamburguesas")
                .description("Normal")
                .active(true)
                .build();

        Product existingProduct = Product.builder()
                .id(1L)
                .name("Pizzas")
                .description("Pizza intaliana")
                .price(new BigDecimal("50000"))
                .stock(23)
                .available(true)
                .category(existingCategory)
                .build();
        ProductRequest request = new ProductRequest();
        request.setName("Pizzas");
        request.setDescription("Pizza intaliana y macarrones");
        request.setPrice(new BigDecimal("55000"));
        request.setStock(10);
        request.setAvailable(true);
        request.setCategoryId(existingCategory.getId());

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existingCategory));
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Assert

        ProductResponse response = productServiceImpl.update(1L, request);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository, times(1)).save(captor.capture());

        Product captured = captor.getValue();

        assertEquals("Pizzas", captured.getName());
        assertEquals("Pizza intaliana y macarrones", captured.getDescription());
        assertEquals(new BigDecimal("55000"), captured.getPrice());
        assertEquals(10, captured.getStock());
        assertTrue(captured.isAvailable());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Pizzas", response.getName());
        assertEquals("Pizza intaliana y macarrones", response.getDescription());
        assertEquals(new BigDecimal("55000"), response.getPrice());
        assertTrue(response.isAvailable());
    }

    @Test
    void shouldDeleteProduct(){
        when(productRepository.existsById(1L))
                .thenReturn(true);

        productServiceImpl.delete(1L);

        verify(productRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingProductThatDoesNotExist() {
        when(productRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(ResourceNotFoundException.class,
                () -> productServiceImpl.delete(1L)
        );
        verify(productRepository, never())
                .deleteById(anyLong());
    }
}
