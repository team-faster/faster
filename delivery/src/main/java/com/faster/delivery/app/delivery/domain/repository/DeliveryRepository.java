package com.faster.delivery.app.delivery.domain.repository;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
  Delivery save(Delivery delivery);
  Optional<Delivery> findByIdAndDeletedAtIsNull(UUID targetDeliveryId);
}
