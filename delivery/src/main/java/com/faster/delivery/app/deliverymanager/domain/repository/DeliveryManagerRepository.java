package com.faster.delivery.app.deliverymanager.domain.repository;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository {
  DeliveryManager save(DeliveryManager deliveryManager);
  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(UUID id);
  long countByHubIdAndType(UUID hubId, DeliveryManager.Type type);
  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(UUID hubId, DeliveryManager.Type type, Integer deliverySequenceNumber);

  Optional<DeliveryManager> findByUserIdAndDeletedAtIsNull(Long userId);
}
