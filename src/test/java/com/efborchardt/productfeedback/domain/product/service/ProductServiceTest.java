package com.efborchardt.productfeedback.domain.product.service;

import com.efborchardt.productfeedback.application.usecase.product.list.ListProductFilterCriteriaDTO;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductRequestDTO;
import com.efborchardt.productfeedback.domain._shared.exception.UnauthorizedActionException;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.repository.ProductRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final User user = new User(
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );

    private final Product product = new Product(
            UUID.randomUUID(),
            "Test Product",
            "Description",
            BigDecimal.TEN,
            user
    );

    private User adminUser;
    private User regularUser;
    private User productCreator;
    private Product createdProduct;
    private UpdateProductRequestDTO updateDto;

    @BeforeEach
    void setUp() {
        adminUser = new User(
                "admin",
                "email@email.com",
                "password",
                UserRole.ADMIN
        );
        regularUser = new User(
                "user",
                "email@email.com",
                "password",
                UserRole.USER
        );
        productCreator = new User(
                "another user",
                "email@email.com",
                "password",
                UserRole.USER
        );

        createdProduct = new Product(UUID.randomUUID(), "Test Product", "Description", BigDecimal.TEN, productCreator);
        updateDto = new UpdateProductRequestDTO(
                createdProduct.getId(),
                "new name",
                "new desc",
                BigDecimal.TEN
        );
    }

    @Test
    void createNewProductSuccessfully() {
        when(productRepository.findProductByName(anyString())).thenReturn(Collections.emptyList());
        productService.createNewProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    void createNewProductFailsWhenProductExists() {
        when(productRepository.findProductByName(product.getName())).thenReturn(List.of(product));
        assertThrows(RuntimeException.class, () -> productService.createNewProduct(product));
    }

    @Test
    void listProductsSuccessfully() {
        when(productRepository.list()).thenReturn(List.of(product));
        List<Product> products = productService.listProducts(new ListProductFilterCriteriaDTO(null, null));
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void listProductsByPriceRangeSuccessfully() {
        when(productRepository.findByPriceRange(BigDecimal.ONE, BigDecimal.TEN)).thenReturn(List.of(product));
        List<Product> products = productService.listProducts(new ListProductFilterCriteriaDTO(BigDecimal.ONE, BigDecimal.TEN));
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void updateProductSuccessfully() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        UpdateProductRequestDTO updateProductRequestDTO = new UpdateProductRequestDTO(
                product.getId(),
                "new name",
                "new description",
                BigDecimal.ONE
        );

        Product updatedProduct = productService.updateProduct(updateProductRequestDTO, user);

        verify(productRepository).save(any(Product.class));
        assertEquals("new name", updatedProduct.getName());
        assertEquals("new description", updatedProduct.getDescription());
        assertEquals(BigDecimal.ONE, updatedProduct.getPrice());
    }

    @Test
    void deleteProductSuccessfully() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        productService.deleteProduct(product.getId(), user);
        verify(productRepository).delete(product);
    }

    @Test
    void updateProductThrowsUnauthorizedWhenNotCreatorOrAdmin() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        assertThrows(UnauthorizedActionException.class, () -> productService.updateProduct(updateDto, regularUser));
    }

    @Test
    void updateProductSucceedsAsCreator() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        assertDoesNotThrow(() -> productService.updateProduct(updateDto, productCreator));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProductSucceedsAsAdmin() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        assertDoesNotThrow(() -> productService.updateProduct(updateDto, adminUser));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProductThrowsUnauthorizedWhenNotCreatorOrAdmin() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        assertThrows(UnauthorizedActionException.class, () -> productService.deleteProduct(createdProduct.getId(), regularUser));
    }

    @Test
    void deleteProductSucceedsAsCreator() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        doNothing().when(productRepository).delete(createdProduct);
        assertDoesNotThrow(() -> productService.deleteProduct(createdProduct.getId(), productCreator));
        verify(productRepository).delete(createdProduct);
    }

    @Test
    void deleteProductSucceedsAsAdmin() {
        when(productRepository.findById(createdProduct.getId())).thenReturn(Optional.of(createdProduct));
        doNothing().when(productRepository).delete(createdProduct);
        assertDoesNotThrow(() -> productService.deleteProduct(createdProduct.getId(), adminUser));
        verify(productRepository).delete(createdProduct);
    }
}