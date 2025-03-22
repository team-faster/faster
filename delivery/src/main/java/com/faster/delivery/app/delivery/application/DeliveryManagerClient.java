package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.AssignDeliveryManagerApplicationResponse;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.infrastructure.client.type.DeliveryManagerType;
import java.util.UUID;

public interface DeliveryManagerClient {

  DeliveryManagerDto getDeliveryManagerData(Long deliveryManagerId);

  AssignDeliveryManagerApplicationResponse assignCompanyDeliveryManager(UUID hubId,  DeliveryManagerType type, int requiredAssignManagerCount);

}
