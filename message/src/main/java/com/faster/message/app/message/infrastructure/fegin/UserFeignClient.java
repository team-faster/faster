package com.faster.message.app.message.infrastructure.fegin;

import com.common.response.ApiResponse;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

  @GetMapping("/internal/users/{userId}")
  ResponseEntity<ApiResponse<IGetUserResponseDto>> getUserById(@PathVariable Long userId);
}
