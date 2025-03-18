package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.HubClient;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HubClientImplForDeliveryManager implements HubClient {

  private final HubFeginClient hubFeginClient;

  @Override
  public HubDto getHubData(UUID hubId) {
    ApiResponse<HubGetResponseDto> hubData = hubFeginClient.getHubData(hubId);
    HubGetResponseDto data = hubData.data();
    return data.toHubDto();
  }
}
