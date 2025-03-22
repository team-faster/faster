package com.faster.delivery.app.deliverymanager.domain.repository;

import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DeliveryManagerRepository {

  DeliveryManager save(DeliveryManager deliveryManager);

  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(Long id);

  long countByHubIdAndType(UUID hubId, DeliveryManager.Type type);

  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(
      UUID hubId, DeliveryManager.Type type, Iterable<Integer> deliverySequenceNumbers);

  // todo: 이거 쓰이는지 확인 필요
  Optional<DeliveryManager> findByUserIdAndDeletedAtIsNull(Long userId);

  Long incrementManagerSequenceByCompanyId(
      UUID hubId, DeliveryManagerType type, int assignableManagerCount);
}
