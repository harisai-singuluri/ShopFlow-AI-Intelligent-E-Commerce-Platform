package com.shopflow.product.service.impl;

import com.shopflow.common.exception.ResourceNotFoundException;
import com.shopflow.common.exception.ProductAlreadyExistsException;
import com.shopflow.product.dto.ProductRequest;
import com.shopflow.product.model.Product;
import com.shopflow.product.repository.ProductRepository;
import com.shopflow.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(ProductRequest request) {
        productRepository.findBySku(request.getSku()).ifPresent(existing -> {
            throw new ProductAlreadyExistsException("Product with SKU '" + request.getSku() + "' already exists");
        });

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .sku(request.getSku())
                .availableQuantity(request.getAvailableQuantity())
                .build();

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product updateProduct(UUID id, ProductRequest request) {
        Product product = getProductById(id);

        productRepository.findBySku(request.getSku())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ProductAlreadyExistsException(
                            "Product with SKU '" + request.getSku() + "' already exists");
                });

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setAvailableQuantity(request.getAvailableQuantity());

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
