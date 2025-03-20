package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.HubClient;
import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.hub.HubPathResponseDto;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.HubGetListResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HubClientImplForDelivery implements HubClient {

  private final HubFeignClient hubFeginClient;

  /**
   * 허브서비스 : 출발허브 ~ 도착 허브 까지의 최적 경로 조회
   * @param sourceHubId
   * @param destinationHubId
   * @return
   */
  public List<HubRouteDto> getHubRouteDataList(UUID sourceHubId, UUID destinationHubId) {
    ApiResponse<HubPathResponseDto> hubRouteData = hubFeginClient.getHubRouteData(
        new HubPathRequestDto(sourceHubId, destinationHubId));
    HubPathResponseDto data = hubRouteData.data();
    return data.toHubRouteDtoList();
  }

  // 허브 정보 (허브 담당자 정보 포함) 조회
  @Override
  public List<HubDto> getHubListData(List<UUID> hubIdList) {
    ApiResponse<HubGetListResponseDto> hubData = hubFeginClient.getHubListData(hubIdList);
    HubGetListResponseDto data = hubData.data();
    return data.toHubDtoList();
  }
}
