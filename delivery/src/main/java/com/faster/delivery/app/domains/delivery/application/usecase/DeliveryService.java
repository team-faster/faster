package com.faster.delivery.app.domains.delivery.application.usecase;

import com.faster.delivery.app.domains.delivery.application.dto.DeliverySaveDto;
import java.util.UUID;

public interface DeliveryService {
  UUID saveDelivery(Long userId, DeliverySaveDto deliverySaveDto);
}
