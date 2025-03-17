package com.faster.delivery.app.deliverymanager.application.usecase;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import java.util.UUID;

public interface DeliveryManagerService {
  UUID saveDeliveryManager(DeliveryManagerSaveDto saveDto);
}
