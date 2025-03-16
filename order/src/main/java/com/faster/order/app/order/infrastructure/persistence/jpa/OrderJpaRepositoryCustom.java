package com.faster.order.app.order.infrastructure.persistence.jpa;

import com.common.resolver.dto.UserRole;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.infrastructure.persistence.jpa.dto.response.OrderQuerydslResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderJpaRepositoryCustom {
  Page<OrderQuerydslResponseDto> getOrdersByConditionAndCompanyId(
      Pageable pageable, SearchOrderConditionDto condition, UUID companyId, UserRole userRole);
}
