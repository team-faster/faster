package com.faster.order.app.order.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import java.util.UUID;

public interface OrderService {

  OrderDetailApplicationResponseDto getOrderById(CurrentUserInfoDto userInfo, UUID orderId);

  void deleteOrderById(CurrentUserInfoDto userInfo, UUID orderId);
}
