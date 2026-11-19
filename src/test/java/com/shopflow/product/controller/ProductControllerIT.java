package com.shopflow.product.controller;

import com.shopflow.base.BaseIntegrationTest;
import com.shopflow.product.dto.ProductRequest;
import com.shopflow.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerIT extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Mouse");
        request.setDescription("Ergonomic mouse");
        request.setPrice(BigDecimal.valueOf(49.99));
        request.setSku("TEST-SKU-001");
        request.setAvailableQuantity(10);

        ResponseEntity<Map<String, Object>> createResponse = restTemplate.exchange(
                "/api/v1/products",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() { }
        );

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getHeaders().getLocation()).isNotNull();
        assertThat(createResponse.getBody()).containsKey("data");
        Map<?, ?> createdProduct = (Map<?, ?>) createResponse.getBody().get("data");
        assertThat(createdProduct.get("name")).isEqualTo("Test Mouse");
        assertThat(createdProduct.get("sku")).isEqualTo("TEST-SKU-001");
        assertThat(productRepository.count()).isEqualTo(1);

        String productId = String.valueOf(createdProduct.get("id"));
        ResponseEntity<Map<String, Object>> getResponse = restTemplate.exchange(
                "/api/v1/products/{id}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() { },
                productId
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).containsKey("data");
        Map<?, ?> retrievedProduct = (Map<?, ?>) getResponse.getBody().get("data");
        assertThat(retrievedProduct.get("name")).isEqualTo("Test Mouse");
        assertThat(retrievedProduct.get("sku")).isEqualTo("TEST-SKU-001");
    }
}