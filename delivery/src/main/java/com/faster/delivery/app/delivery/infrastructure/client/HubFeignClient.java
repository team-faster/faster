package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubGetListResponseDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service", contextId = "hubClientForDelivery", configuration = FeignClientConfig.class)
public interface HubFeignClient {
  @GetMapping("/internal/hubs/paths")
  ApiResponse<HubPathResponseDto> getHubRouteData(
      @RequestParam("source-hub-id") UUID sourceHubId,
      @RequestParam("destination-hub-id") UUID destinationHubId);

  @GetMapping("/internal/hubs")
  ApiResponse<HubGetListResponseDto> getHubListData(@RequestParam("hubs") List<UUID> hubIdList);

  @GetMapping("/internal/hubs")
  ResponseEntity<ApiResponse<HubGetListResponseDto>> getHubByHubManagerId(
      @RequestParam("hub-manager-id") Long userId);
}
