package com.faster.delivery.app.deliverymanager.application.dto;

import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryManagerElementDto(
    Long deliveryManagerId, // "배송담당자 고유 식별 번호",
    UUID hubId, // "허브 고유 식별 번호",
    String type, // "배송담당자 유형",
    Integer deliverySequenceNumber, // "배송순번번호",
    String createdAt, // "배송담당자 생성일자",
    String updatedAt, // "배송담당자 수정일자",
    Long createdBy, // "배송담당자 생성자",
    Long updatedBy // "배송담당자 수정자",
) {
  public static DeliveryManagerElementDto from(DeliveryManager deliveryManager) {
    return DeliveryManagerElementDto.builder()
        .deliveryManagerId(deliveryManager.getId())
        .hubId(deliveryManager.getHubId())
        .type(deliveryManager.getType().name())
        .deliverySequenceNumber(deliveryManager.getDeliverySequenceNumber())
        .createdAt(deliveryManager.getCreatedAt().toString())
        .createdBy(deliveryManager.getCreatedBy())
        .updatedAt(deliveryManager.getUpdatedAt().toString())
        .updatedBy(deliveryManager.getUpdatedBy())
        .build();
  }
}
