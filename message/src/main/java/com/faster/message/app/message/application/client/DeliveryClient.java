package com.faster.message.app.message.application.client;

import com.faster.message.app.message.application.dto.response.AGetDeliveryResponseDto;
import java.util.UUID;

public interface DeliveryClient {

  AGetDeliveryResponseDto getDeliveryByOrderId(UUID deliveryId);

}
