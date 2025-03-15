package com.faster.order.app.order.infrastructure.persistence.jpa;

import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID>, OrderRepository {

  Optional<Order> findByIdAndDeletedAtIsNull(UUID orderId);
}
