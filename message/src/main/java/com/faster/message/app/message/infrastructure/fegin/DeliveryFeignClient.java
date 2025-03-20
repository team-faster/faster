package com.faster.message.app.message.infrastructure.fegin;

import com.common.response.ApiResponse;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetDeliveryResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-service", contextId = "deliveryService")
public interface DeliveryFeignClient {

  @GetMapping("/api/deliveries/{deliveryId}")
  ResponseEntity<ApiResponse<IGetDeliveryResponseDto>> getDeliveryDetail(
      @PathVariable UUID deliveryId);

}
