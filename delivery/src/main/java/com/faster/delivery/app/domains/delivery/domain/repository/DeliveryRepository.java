package com.faster.delivery.app.domains.delivery.domain.repository;

import com.faster.delivery.app.domains.delivery.domain.entity.Delivery;

public interface DeliveryRepository {
  Delivery save(Delivery delivery);
}
