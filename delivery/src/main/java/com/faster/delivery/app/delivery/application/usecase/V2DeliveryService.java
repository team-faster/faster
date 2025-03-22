package com.faster.delivery.app.delivery.application.usecase;

import com.faster.delivery.app.delivery.application.dto.AssignedDeliveryRouteDto;
import java.util.List;

// todo: 충돌방지를 위해 일단 분리했습니다.
public interface V2DeliveryService {
  public List<AssignedDeliveryRouteDto> assignHubDeliveryManagerScheduleService();
}
