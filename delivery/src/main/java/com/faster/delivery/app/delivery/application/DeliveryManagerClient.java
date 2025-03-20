package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import java.util.UUID;

public interface DeliveryManagerClient {
  DeliveryManagerDto getDeliveryManagerData(UUID deliveryManagerId);

  DeliveryManagerDto getDeliveryManagerByUserId(Long aLong);
}
