package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-service", contextId = "hubClientForDelivery", configuration = FeignClientConfig.class)
public interface HubFeginClient {
  @PostMapping("/internal/hubs/paths")
  ApiResponse<HubPathResponseDto> getHubRouteData(@RequestBody HubPathRequestDto hubPathRequestDto);
}
