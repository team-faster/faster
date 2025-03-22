package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.AssignDeliveryManagerFeignRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.AssignDeliveryManagerFeignResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", configuration = FeignClientConfig.class)
public interface DeliveryManagerFeignClient {

  @PostMapping("/internal/delivery-managers/{deliveryManagerId}")
  ResponseEntity<ApiResponse<DeliveryManagerGetResponseDto>> getDeliveryManagerData(@PathVariable("deliveryManagerId") Long deliveryManagerId);

  @PostMapping("/internal/delivery-managers/assign")
  ResponseEntity<ApiResponse<AssignDeliveryManagerFeignResponse>> assignCompanyDeliveryManager(
      @RequestBody AssignDeliveryManagerFeignRequestDto dto);
}
