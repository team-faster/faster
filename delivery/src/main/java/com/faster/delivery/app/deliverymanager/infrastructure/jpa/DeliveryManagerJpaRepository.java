package com.faster.delivery.app.deliverymanager.infrastructure.jpa;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryManagerJpaRepository extends DeliveryManagerRepository, JpaRepository<DeliveryManager, UUID>, DeliveryManagerRepositoryCustom {
  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(UUID id);

  Optional<DeliveryManager> findByUserIdAndDeletedAtIsNull(Long userId);
}
