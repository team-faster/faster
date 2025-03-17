package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.UserGetResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeginClient {
  @GetMapping("/internal/user/{userId}")
  ApiResponse<UserGetResponseDto> getUserData(@PathVariable("userId") Long userId);
}
