package com.faster.delivery.app.delivery.infrastructure.jpa;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepositoryCustom {
  Optional<Delivery> findByDeliveryId(UUID targetDeliveryId);
  Page<Delivery> searchDeliveryList(Pageable pageable, String role,
      Long companyDeliveryManagerId, UUID receiptCompanyId, UUID hubId, Delivery.Status status);
}
