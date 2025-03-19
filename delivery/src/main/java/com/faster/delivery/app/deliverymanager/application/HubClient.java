package com.faster.delivery.app.deliverymanager.application;

import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import java.util.List;
import java.util.UUID;

public interface HubClient {
  List<HubDto> getHubListData(List<UUID> hubIdList);
}
