package com.faster.delivery.app.delivery.domain.repository;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepository {
  Delivery save(Delivery delivery);
  Optional<Delivery> findByIdAndDeletedAtIsNull(UUID targetDeliveryId);
  void delete(Delivery delivery);
//  Page<Delivery> searchDeliveryList(Pageable pageable, String role,
//      UUID companyDeliveryManagerId, UUID receiptCompanyId, UUID hubId, Delivery.Status status);
}
