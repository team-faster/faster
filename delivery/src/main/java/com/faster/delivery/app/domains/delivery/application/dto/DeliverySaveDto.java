package com.faster.delivery.app.domains.delivery.application.dto;

import com.faster.delivery.app.domains.delivery.presentaion.dto.DeliverySaveRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveDto(UUID orderId,
                              UUID sourceHubId,
                              UUID destinationHubId,
                              UUID receiveCompanyId) {

  public static DeliverySaveDto from(DeliverySaveRequestDto deliverySaveRequestDto) {
    return DeliverySaveDto.builder()
        .orderId(deliverySaveRequestDto.orderId())
        .sourceHubId(deliverySaveRequestDto.sourceHubId())
        .destinationHubId(deliverySaveRequestDto.destinationHubId())
        .receiveCompanyId(deliverySaveRequestDto.receiveCompanyId())
        .build();
  }
}
