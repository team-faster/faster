package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service", contextId = "HubFeignClientForDeliveryManager")
public interface HubFeginClient {
  @GetMapping("/internal/hubs/{hubId}")
  ApiResponse<HubGetResponseDto> getHubData(@PathVariable("hubId") UUID hubId);
}
