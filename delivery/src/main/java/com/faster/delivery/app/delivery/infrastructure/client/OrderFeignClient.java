package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.presentaion.dto.internal.OrderUpdateRequestDto;
import com.faster.delivery.app.delivery.presentaion.dto.internal.OrderUpdateResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", configuration = FeignClientConfig.class)
public interface OrderFeignClient {

  @PatchMapping("/internal/orders/{orderId}/status")
  ApiResponse<OrderUpdateResponseDto> updateOrderStatus(@RequestBody OrderUpdateRequestDto requestDto, @PathVariable("orderId") UUID orderId);
}
