package com.faster.delivery.app.delivery.infrastructure.jpa;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepositoryCustom {
  Optional<Delivery> findByDeliveryId(UUID targetDeliveryId);
}
