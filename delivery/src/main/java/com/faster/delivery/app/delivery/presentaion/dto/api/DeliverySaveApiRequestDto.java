package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveApiRequestDto(
    UUID orderId,
    UUID sourceHubId,
    UUID destinationHubId,
    UUID receiveCompanyId
) {
  public DeliverySaveDto toSaveDto() {
    return DeliverySaveDto.builder()
        .orderId(this.orderId)
        .sourceHubId(this.sourceHubId)
        .destinationHubId(this.destinationHubId)
        .receiveCompanyId(this.receiveCompanyId)
        .build();
  }
}
