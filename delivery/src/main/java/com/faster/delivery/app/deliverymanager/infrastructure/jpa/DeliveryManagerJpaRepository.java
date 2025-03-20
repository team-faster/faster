package com.faster.delivery.app.deliverymanager.infrastructure.jpa;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryManagerJpaRepository extends DeliveryManagerRepository, JpaRepository<DeliveryManager, UUID>, DeliveryManagerRepositoryCustom {
  @Query("select dm from DeliveryManager dm where dm.hubId = :hubId and dm.type = :type and dm.deliverySequenceNumber = :deliverySequenceNumber")
  List<DeliveryManager> findAllByHubIdAndTypeAndDeliverySequenceNumber(UUID hubId, DeliveryManager.Type type, Integer deliverySequenceNumber);
}
