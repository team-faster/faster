package com.faster.order.app.order.application.usecase;

import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import java.util.UUID;

public interface OrderService {

  OrderDetailApplicationResponseDto getOrderById(UUID orderId);
}
