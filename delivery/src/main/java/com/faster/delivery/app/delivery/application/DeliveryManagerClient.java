package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import java.util.UUID;

public interface DeliveryManagerClient {

  // todo: 수정 필요
  DeliveryManagerDto getDeliveryManagerData(UUID deliveryManagerId);
  DeliveryManagerDto getDeliveryManagerData(Long deliveryManagerId);

  DeliveryManagerDto assignCompanyDeliveryManager(UUID companyId);

  DeliveryManagerDto getDeliveryManagerByUserId(Long aLong);
}
