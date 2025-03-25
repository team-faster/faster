package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.AssignedDeliveryRouteDto;
import com.faster.delivery.app.delivery.application.usecase.DeliveryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryScheduler {

  private final DeliveryService deliveryService;

  @Scheduled(cron = "0 0 10,16 * * ?") // 매일 오전 10시, 오후 4시 실행
  public void assignHubDelivery() {
    log.info("Scheduled task executed at: {}", LocalDateTime.now());
    List<AssignedDeliveryRouteDto> assignedRoutes = deliveryService.assignHubDeliveryManagerScheduleService();
    log.info("Assigned Hub Delivery Routes : {}", assignedRoutes.size());
  }
}
