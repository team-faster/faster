package com.faster.product.app.product.domain.repository;

import com.faster.product.app.product.domain.entity.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

  Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

  Product save(Product product);
}
