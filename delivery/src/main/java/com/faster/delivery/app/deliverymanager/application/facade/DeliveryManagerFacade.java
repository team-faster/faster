package com.faster.delivery.app.deliverymanager.application.facade;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;

public interface DeliveryManagerFacade {
  Long saveDeliveryManager(DeliveryManagerSaveDto saveDto);
}
