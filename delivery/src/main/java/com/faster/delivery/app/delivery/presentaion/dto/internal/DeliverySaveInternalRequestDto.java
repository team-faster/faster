package com.faster.delivery.app.delivery.presentaion.dto.internal;

import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveInternalRequestDto(
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
