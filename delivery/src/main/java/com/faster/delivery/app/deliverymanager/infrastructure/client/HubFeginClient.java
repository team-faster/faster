package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetListResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service", contextId = "HubFeignClientForDeliveryManager", configuration = FeignClientConfig.class)
public interface HubFeginClient {

  @GetMapping("/internal/hubs")
  ApiResponse<HubGetListResponseDto> getHubListData(@RequestParam("hubs") List<UUID> hubIdList);
}
