package com.faster.delivery.app.delivery.application.usecase;

import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import java.util.UUID;

public interface DeliveryService {
  UUID saveDelivery(Long userId, DeliverySaveDto deliverySaveDto);
}
