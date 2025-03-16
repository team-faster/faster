package com.faster.delivery.app.domains.delivery.infrastructure.feign;

import com.common.response.ApiResponse;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.hub.HubPathResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-service")
public interface HubClient {
  @PostMapping("/internal/hubs/paths")
  ApiResponse<HubPathResponseDto> getHubRouteData(@RequestBody HubPathRequestDto hubPathRequestDto);
}
