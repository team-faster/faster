package com.faster.delivery.app.delivery.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import java.util.UUID;

public interface DeliveryService {
  UUID saveDelivery(Long userId, DeliverySaveDto deliverySaveDto);
  UUID updateDeliveryStatus (
      CurrentUserInfoDto userInfoDto, UUID deliveryId, DeliveryUpdateDto deliveryUpdateDto);
}
