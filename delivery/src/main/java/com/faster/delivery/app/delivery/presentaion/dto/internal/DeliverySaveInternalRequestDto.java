package com.faster.delivery.app.delivery.presentaion.dto.internal;

import com.faster.delivery.app.delivery.application.dto.DeliverySaveApplicationDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveInternalRequestDto(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiveCompanyId) {

  public DeliverySaveApplicationDto toApplicationDto() {
    return DeliverySaveApplicationDto.builder()
        .orderId(this.orderId())
        .supplierCompanyId(this.supplierCompanyId())
        .receiveCompanyId(this.receiveCompanyId())
        .build();
  }
}
