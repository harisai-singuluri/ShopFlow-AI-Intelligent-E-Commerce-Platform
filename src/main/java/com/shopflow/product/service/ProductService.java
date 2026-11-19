package com.shopflow.product.service;

import com.shopflow.product.dto.ProductRequest;
import com.shopflow.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

/**
 * Service interface for product‑catalogue business logic.
 *
 * <p>Separating the contract (interface) from the implementation lets senior engineers:
 *   • swap implementations (e.g., for a micro‑service later) without changing controllers.
 *   • write unit tests against the interface using mocks.
 *   • keep controllers thin and focused on request/response mapping.
 * </p>
 */
public interface ProductService {
    Product createProduct(ProductRequest request);
    Product getProductById(UUID id);
    Page<Product> getAllProducts(Pageable pageable);
    Product updateProduct(UUID id, ProductRequest request);
    void deleteProduct(UUID id);
}
