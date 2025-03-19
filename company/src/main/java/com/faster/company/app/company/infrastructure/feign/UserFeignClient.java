package com.faster.company.app.company.infrastructure.feign;

import com.common.response.ApiResponse;
import com.faster.company.app.company.infrastructure.feign.dto.response.GetUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

  @GetMapping("/internal/users/{userId}")
  ResponseEntity<ApiResponse<GetUserResponseDto>> getUserById(@PathVariable Long userId);

}
