package com.faster.delivery.app.domains.delivery.presentaion.dto;

import com.faster.delivery.app.domains.delivery.application.dto.DeliverySaveDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveRequestDto(
    UUID orderId,
    UUID sourceHubId,
    UUID destinationHubId,
    UUID receiveCompanyId) {

  public DeliverySaveDto toSaveDto() {
    return DeliverySaveDto.builder()
        .orderId(this.orderId())
        .sourceHubId(this.sourceHubId())
        .destinationHubId(this.destinationHubId())
        .receiveCompanyId(this.receiveCompanyId())
        .build();
  }
}
