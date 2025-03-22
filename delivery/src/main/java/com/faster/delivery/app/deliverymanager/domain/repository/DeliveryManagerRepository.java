package com.faster.delivery.app.deliverymanager.domain.repository;

import com.faster.delivery.app.deliverymanager.domain.criteria.DeliveryManagerCriteria;
import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryManagerRepository {
  DeliveryManager save(DeliveryManager deliveryManager);

  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(Long id);

  long countByHubIdAndType(UUID hubId, DeliveryManager.Type type);
  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(UUID hubId, DeliveryManager.Type type, Integer deliverySequenceNumber);
  Page<DeliveryManager> searchDeliveryManagerList(DeliveryManagerCriteria criteria, Pageable pageable);

  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(
      UUID hubId, DeliveryManager.Type type, Iterable<Integer> deliverySequenceNumbers);

  Long incrementManagerSequenceByCompanyId(
      UUID hubId, DeliveryManagerType type, int assignableManagerCount);

  Integer findMaxDeliverySequenceNumberByHubId(UUID hubId);
}
