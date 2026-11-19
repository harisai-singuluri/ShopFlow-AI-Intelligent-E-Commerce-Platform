package com.shopflow.product.repository;

import com.shopflow.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link Product}.
 *
 * <p>Provides basic CRUD methods out of the box. Custom queries are added as needed.
 * Keeping the repository thin follows the senior‑engineer guideline of "fat services, thin repositories".</p>
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySku(String sku);
}
