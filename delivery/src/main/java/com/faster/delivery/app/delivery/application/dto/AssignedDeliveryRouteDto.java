package com.faster.delivery.app.delivery.application.dto;

import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AssignedDeliveryRouteDto(
    UUID deliveryId,
    Integer sequence,
    UUID sourceHubId,
    UUID destinationHubId,
    Type type,
    Long deliveryManagerId,
    String deliveryManagerName,
    LocalDateTime createdAt,
    Long createdBy
) {

  public static AssignedDeliveryRouteDto from(DeliveryRoute route) {
    return AssignedDeliveryRouteDto.builder()
        .deliveryId(route.getDelivery().getId())
        .sequence(route.getSequence())
        .sourceHubId(route.getSourceHubId())
        .destinationHubId(route.getDestinationHubId())
        .type(Type.valueOf(route.getType().name()))
        .deliveryManagerId(route.getDeliveryManagerId())
        .deliveryManagerName(route.getDeliveryManagerName())
        .createdAt(route.getCreatedAt())
        .createdBy(route.getCreatedBy())
        .build();
  }

  public enum Type {
    TO_HUB,
    TO_COMPANY // 사용되는일 없을듯 : 업체배송은 Delivery.Status 를 보고 업체 배송 판단
  }
}
