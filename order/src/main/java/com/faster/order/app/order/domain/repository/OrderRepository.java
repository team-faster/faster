package com.faster.order.app.order.domain.repository;

import com.common.resolver.dto.UserRole;
import com.faster.order.app.order.domain.criteria.SearchOrderCriteria;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.projection.SearchOrderProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

  Page<SearchOrderProjection> getOrdersByConditionAndCompanyId(Pageable pageable,
      SearchOrderCriteria condition, UUID companyId, UserRole userRole);

  Optional<Order> findByIdAndDeletedAtIsNull(UUID orderId);

  Optional<Order> findByIdAndDeletedAtIsNullFetchJoin(UUID orderId);

  Optional<Order> findByIdAndStatusAndDeletedAtIsNull(UUID orderId, OrderStatus orderStatus);

  Order save(Order order);

  <S extends Order> List<S> saveAll(Iterable<S> entities);

  Optional<Order> findByIdAndStatusAndDeletedAtIsNullFetchJoin(UUID orderId, OrderStatus orderStatus);

  Optional<Order> findByIdAndReceivingCompanyIdAndDeletedAtIsNullFetchJoin(UUID orderId, UUID id);
}
