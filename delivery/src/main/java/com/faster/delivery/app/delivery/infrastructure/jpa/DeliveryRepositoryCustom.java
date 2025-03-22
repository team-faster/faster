package com.faster.delivery.app.delivery.infrastructure.jpa;

import com.faster.delivery.app.delivery.domain.criteria.DeliveryCriteria;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepositoryCustom {
  Optional<Delivery> findByDeliveryId(UUID targetDeliveryId);
  Page<Delivery> searchDeliveryList(DeliveryCriteria criteria, Pageable pageable);

  List<DeliveryRoute> findRoutesWithMissingManager();
}
