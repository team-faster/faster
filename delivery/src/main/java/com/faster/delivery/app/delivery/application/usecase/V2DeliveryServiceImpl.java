package com.faster.delivery.app.delivery.application.usecase;

import com.faster.delivery.app.delivery.application.dto.AssignedDeliveryRouteDto;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// todo: 충돌방지를 위해 일단 분리했습니다.
@Service
@RequiredArgsConstructor
public class V2DeliveryServiceImpl implements V2DeliveryService {

  private final DeliveryRepository deliveryRepository;

  @Override
  @Transactional
  public List<AssignedDeliveryRouteDto> assignHubDeliveryManagerScheduleService() {
    List<DeliveryRoute> routes = deliveryRepository.findRoutesWithMissingManager();

    // 출발지로 분리
    Map<UUID, List<DeliveryRoute>> deliveryRoutesOfSourceHubId = routes.stream()
        .collect(Collectors.groupingBy(DeliveryRoute::getSourceHubId));
    for(Entry<UUID, List<DeliveryRoute>> entry : deliveryRoutesOfSourceHubId.entrySet()){
      // 배송 담당자 배정 요청 명수
      int requireCount = entry.getValue().size();

      // 목적지로 분리
      Map<UUID, List<DeliveryRoute>> deliveryRoutesOfDestinationHubId = entry.getValue().stream()
          .collect(Collectors.groupingBy(DeliveryRoute::getDestinationHubId));

      // 출발지 - 목적지 같으면 하나의 배정담당자에게 지정
      for(Entry<UUID, List<DeliveryRoute>> sameRoutesEntry : deliveryRoutesOfDestinationHubId.entrySet()){

      }
    }

    return null;
  }

}
