package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.HubDto;
import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import java.util.List;
import java.util.UUID;

public interface HubClient {
  List<HubRouteDto> getHubRouteDataList(UUID sourceHubId, UUID destinationHubId);
  List<HubDto> getHubListData(List<UUID> hubIdList);

  List<HubDto> getHubByHubManagerId(Long aLong);
}
