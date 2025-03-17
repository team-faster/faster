package com.faster.delivery.app.delivery.domain.repository;

import com.faster.delivery.app.delivery.domain.entity.Delivery;

public interface DeliveryRepository {
  Delivery save(Delivery delivery);
}
