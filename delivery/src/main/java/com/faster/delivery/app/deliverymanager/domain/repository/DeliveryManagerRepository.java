package com.faster.delivery.app.deliverymanager.domain.repository;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository {
  DeliveryManager save(DeliveryManager deliveryManager);
  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(UUID id);

  Optional<DeliveryManager> findByUserIdAndDeletedAtIsNull(Long userId);
}
