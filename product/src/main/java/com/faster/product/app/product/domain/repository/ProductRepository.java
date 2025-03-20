package com.faster.product.app.product.domain.repository;

import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.product.app.product.domain.criteria.SearchProductCriteria;
import com.faster.product.app.product.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

  Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

  Product save(Product product);

  List<Product> findByIdInAndDeletedAtIsNull(Set<UUID> ids);

  Page<Product> getProductsByConditionAndCompanyId(
      Pageable pageable, SearchProductCriteria criteria, UUID companyId, UserRole role);

  Optional<Product> findByIdAndDeletedAtIsNullWithPessimisticLock(UUID productId);

  <S extends Product> List<S> saveAll(Iterable<S> entities);

  void updateProductHubByCompanyId(UUID companyId, UUID hubId, Long userId);
}
