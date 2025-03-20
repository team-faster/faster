package com.faster.message.app.message.infrastructure.fegin;

import com.common.response.ApiResponse;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetDeliveryManagerResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-service", contextId = "deliveryManagerService")
public interface DeliveryManagerFeignClient {

  @GetMapping("/internal/delivery-managers/{deliveryManagerId}")
  ResponseEntity<ApiResponse<IGetDeliveryManagerResponseDto>> getDeliveryManagerDetails(
      @PathVariable("deliveryManagerId") UUID deliveryManagerId
  );
}
