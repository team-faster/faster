package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.HubClient;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetListResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HubClientImplForDeliveryManager implements HubClient {

  private final HubFeginClient hubFeginClient;

  @Override
  public List<HubDto> getHubListData(List<UUID> hubIdList) {
    ApiResponse<HubGetListResponseDto> hubData = hubFeginClient.getHubListData(hubIdList);
    HubGetListResponseDto data = hubData.data();
    return data.toHubDtoList();
  }
}
