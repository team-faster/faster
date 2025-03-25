package com.faster.hub.app.hub.infrastructure.client;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.NaverDirectionApiErrorCode;
import com.faster.hub.app.hub.application.usecase.DirectionsApiClient;
import com.faster.hub.app.hub.application.usecase.dto.response.DirectionsApiApplicationResponseDto;
import com.faster.hub.app.hub.infrastructure.client.feign.NaverDirectionsApiFeignClient;
import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverDirectionsApiClientImpl implements DirectionsApiClient {

  private final NaverDirectionsApiFeignClient directionsApiFeignClient;

  @Override
  @Retry(name = "simpleRetryConfig", fallbackMethod = "fallback")
  public DirectionsApiApplicationResponseDto getDrivingRoute(String start, String goal) {
    return directionsApiFeignClient.getDrivingRoute(start, goal).toApplicationDto();
  }

  DirectionsApiApplicationResponseDto fallback(String start, String goal,
      FeignException.FeignServerException e) {
    log.error("FeignServerException:{}:start:{}:goal:{}", e.getMessage(), start, goal);
    // 추후 재시도 이벤트 발생하도록 변경
    throw new CustomException(NaverDirectionApiErrorCode.SERVICE_UNAVAILABLE);
  }

}
