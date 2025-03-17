package com.faster.delivery.app.deliverymanager.application;

import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import java.util.UUID;

public interface HubClient {
  HubDto getHubData(UUID hubId);
}
