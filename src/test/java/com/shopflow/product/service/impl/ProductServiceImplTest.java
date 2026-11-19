package com.shopflow.product.service.impl;

import com.shopflow.common.exception.ProductAlreadyExistsException;
import com.shopflow.common.exception.ResourceNotFoundException;
import com.shopflow.product.dto.ProductRequest;
import com.shopflow.product.model.Product;
import com.shopflow.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductPersistsRequestWhenSkuIsAvailable() {
        ProductRequest request = request();
        UUID productId = UUID.randomUUID();
        when(productRepository.findBySku(request.getSku())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(productId);
            return product;
        });

        Product created = productService.createProduct(request);

        assertEquals(productId, created.getId());
        assertEquals(request.getSku(), created.getSku());
        assertEquals(request.getPrice(), created.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProductRejectsDuplicateSku() {
        ProductRequest request = request();
        when(productRepository.findBySku(request.getSku())).thenReturn(Optional.of(new Product()));

        assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(request));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductByIdFailsWhenProductDoesNotExist() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    private ProductRequest request() {
        return ProductRequest.builder()
                .name("Laptop")
                .description("Portable computer")
                .price(new BigDecimal("999.99"))
                .sku("LAP-001")
                .availableQuantity(10)
                .build();
    }
}
