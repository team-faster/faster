package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.OrderUpdateApplicationRequestDto;
import com.faster.delivery.app.delivery.application.dto.OrderUpdateApplicationResponseDto;
import java.util.UUID;

public interface OrderClient {

  OrderUpdateApplicationResponseDto updateOrderStatus(
      UUID orderId, OrderUpdateApplicationRequestDto requestDto);
}
