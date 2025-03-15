package com.faster.order.app.order.domain.repository;

import com.faster.order.app.order.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Optional<Order> findByIdAndDeletedAtIsNull(UUID orderId);

  Optional<Order> findByIdAndDeletedAtIsNullFetchJoin(UUID orderId);
}
