package com.faster.message.app.message.application.client;

import com.faster.message.app.message.application.dto.response.AGetOrderResponseDto;
import java.util.UUID;

public interface OrderClient {

   AGetOrderResponseDto getOrderByOrderId(UUID orderId);

}
