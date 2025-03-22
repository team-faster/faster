package com.faster.delivery.app.deliverymanager.infrastructure.jpa;

import com.faster.delivery.app.deliverymanager.domain.criteria.DeliveryManagerCriteria;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryManagerRepositoryCustom {
  Integer getNextDeliveryManagerSequence();
  Page<DeliveryManager> searchDeliveryManagerList(DeliveryManagerCriteria criteria, Pageable pageable);
}
