package com.faster.delivery.app.deliverymanager.domain.repository;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;

public interface DeliveryManagerRepository {
  DeliveryManager save(DeliveryManager deliveryManager);
}
