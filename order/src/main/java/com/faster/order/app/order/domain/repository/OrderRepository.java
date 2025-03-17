package com.faster.order.app.order.domain.repository;

import com.common.resolver.dto.UserRole;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.infrastructure.persistence.jpa.dto.response.OrderQuerydslResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

  Page<OrderQuerydslResponseDto> getOrdersByConditionAndCompanyId(Pageable pageable,
      SearchOrderConditionDto condition, UUID companyId, UserRole userRole);

  Optional<Order> findByIdAndDeletedAtIsNull(UUID orderId);

  Optional<Order> findByIdAndDeletedAtIsNullFetchJoin(UUID orderId);

  Optional<Order> findByIdAndStatusAndDeletedAtIsNull(UUID orderId, OrderStatus orderStatus);

  <S extends Order> List<S> saveAll(Iterable<S> entities);
}
