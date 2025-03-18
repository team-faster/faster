package com.faster.product.app.product.infrastructure.persistence.jpa;

import com.common.resolver.dto.UserRole;
import com.faster.product.app.product.domain.criteria.SearchProductCriteria;
import com.faster.product.app.product.domain.entity.Product;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductJpaRepositoryCustom {

  Page<Product> getProductsByConditionAndCompanyId(
      Pageable pageable, SearchProductCriteria criteria, UUID companyId, UserRole role);
}
