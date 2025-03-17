package com.faster.product.app.product.infrastructure.persistence.jpa;

import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository  extends JpaRepository<Product, UUID>,
    ProductJpaRepositoryCustom, ProductRepository {

  Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);
}
