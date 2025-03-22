package com.faster.delivery.app.deliverymanager.infrastructure.jpa;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import com.faster.delivery.app.deliverymanager.infrastructure.redis.ManagerSequenceRepository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryManagerJpaRepository extends DeliveryManagerRepository, JpaRepository<DeliveryManager, Long>, DeliveryManagerRepositoryCustom, ManagerSequenceRepository {
  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(Long id);

  @Query("SELECT dm FROM DeliveryManager dm WHERE dm.hubId = :hubId AND dm.type = :type AND dm.deliverySequenceNumber IN :deliverySequenceNumbers")
  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(UUID hubId, DeliveryManager.Type type, Iterable<Integer> deliverySequenceNumbers);
}
