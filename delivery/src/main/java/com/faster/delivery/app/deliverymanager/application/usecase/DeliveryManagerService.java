package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import java.util.UUID;

public interface DeliveryManagerService {
  UUID saveDeliveryManager(DeliveryManagerSaveDto saveDto);
  DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, UUID deliveryManagerId);
  UUID updateDeliveryManager(UUID deliveryManagerId, DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo);
  UUID deleteDeliveryManager(UUID deliveryManagerId, CurrentUserInfoDto userInfo);
}
