package com.faster.order.app.order.infrastructure.persistence.jpa;

import com.common.resolver.dto.UserRole;
import com.faster.order.app.order.domain.criteria.SearchOrderCriteria;
import com.faster.order.app.order.domain.projection.SearchOrderProjection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderJpaRepositoryCustom {
  Page<SearchOrderProjection> getOrdersByConditionAndCompanyId(
      Pageable pageable, SearchOrderCriteria condition, UUID companyId, UserRole userRole);
}
