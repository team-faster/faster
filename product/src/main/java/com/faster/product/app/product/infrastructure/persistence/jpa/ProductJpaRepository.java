package com.faster.product.app.product.infrastructure.persistence.jpa;

import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository  extends JpaRepository<Product, UUID>,
    ProductJpaRepositoryCustom, ProductRepository {

  Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Product p where p.id = :productId and p.deletedAt is null")
  Optional<Product> findByIdAndDeletedAtIsNullWithPessimisticLock(UUID productId);

  @Modifying
  @Query("update Product p set p.hubId = :hubId, p.updatedAt = current_timestamp, p.updatedBy = :userId where p.companyId = :companyId")
  void updateProductHubByCompanyId(UUID companyId, UUID hubId, Long userId);
}
