package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathResponseDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetListResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service", contextId = "hubClientForDelivery", configuration = FeignClientConfig.class)
public interface HubFeginClient {
  @PostMapping("/internal/hubs/paths")
  ApiResponse<HubPathResponseDto> getHubRouteData(@RequestBody HubPathRequestDto hubPathRequestDto);

  @GetMapping("/internal/hubs")
  ApiResponse<HubGetListResponseDto> getHubListData(@RequestParam("hubs") List<UUID> hubIdList);
}
