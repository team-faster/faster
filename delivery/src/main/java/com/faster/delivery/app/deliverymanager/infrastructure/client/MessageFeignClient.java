package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.SendMessageFeignRequestDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service", configuration = FeignClientConfig.class)
public interface MessageFeignClient {

  @PostMapping("/internal/messages")
  ApiResponse<Void> sendMessage(@RequestBody SendMessageFeignRequestDto dto);
}
