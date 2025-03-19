package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "delivery-service", configuration = FeignClientConfig.class)
public interface DeliveryManagerFeignClient {
  @PostMapping("/internal/delivery-managers/{deliveryManagerId}")
  ApiResponse<DeliveryManagerGetResponseDto> getDeliveryManagerData(@PathVariable("deliveryManagerId") UUID deliveryManagerId);
}
