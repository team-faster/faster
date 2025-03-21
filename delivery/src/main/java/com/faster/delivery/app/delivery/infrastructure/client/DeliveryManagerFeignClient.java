package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.deliverymanager.DeliveryManagerGetResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service", configuration = FeignClientConfig.class)
public interface DeliveryManagerFeignClient {

  // todo: UUID, Long 수정 필요
  @PostMapping("/internal/delivery-managers/{deliveryManagerId}")
  ApiResponse<DeliveryManagerGetResponseDto> getDeliveryManagerData(@PathVariable("deliveryManagerId") Long deliveryManagerId);
  @PostMapping("/internal/delivery-managers/{deliveryManagerId}")
  ApiResponse<DeliveryManagerGetResponseDto> getDeliveryManagerData(@PathVariable("deliveryManagerId") UUID deliveryManagerId);

  @PostMapping("/internal/delivery-managers/assign")
  ApiResponse<DeliveryManagerGetResponseDto> assignCompanyDeliveryManager(@PathVariable("company-id") UUID companyId);

  @GetMapping("/internal/delivery-managers")
  ResponseEntity<ApiResponse<DeliveryManagerGetResponseDto>> getDeliveryManagerByUserId(
      @RequestParam(name="user-id") Long userId);
}
