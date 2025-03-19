package com.faster.hub.app.hub.infrastructure.client;

import com.faster.hub.app.hub.application.usecase.DirectionsApiClient;
import com.faster.hub.app.hub.application.usecase.dto.response.DirectionsApiApplicationResponseDto;
import com.faster.hub.app.hub.infrastructure.client.feign.DirectionsApiFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectionsApiClientImpl implements DirectionsApiClient {
  private final DirectionsApiFeignClient directionsApiFeignClient;

  @Override
  public DirectionsApiApplicationResponseDto getDrivingRoute(String start, String goal) {
    return directionsApiFeignClient.getDrivingRoute(start, goal).toApplicationDto();
  }
}
