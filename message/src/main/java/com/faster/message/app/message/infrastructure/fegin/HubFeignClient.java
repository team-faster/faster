package com.faster.message.app.message.infrastructure.fegin;

import com.common.response.ApiResponse;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetHubResponseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

  @GetMapping
  ResponseEntity<ApiResponse<IGetHubResponseDto>> getHubs(
      @RequestParam(name = "hubs", required = true) List<UUID> hubIds);
}
