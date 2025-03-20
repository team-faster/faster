package com.faster.message.app.message.application.client;

import com.faster.message.app.message.application.dto.response.AGetDeliveryManagerResponseDto;
import java.util.UUID;

public interface DeliveryManagerClient {

  AGetDeliveryManagerResponseDto getOrderByOrderId(UUID deliveryManagerId);

}
