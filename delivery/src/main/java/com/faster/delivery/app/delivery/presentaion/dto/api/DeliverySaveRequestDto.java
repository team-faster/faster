package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliverySaveApplicationDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveRequestDto(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiveCompanyId) {

  public DeliverySaveApplicationDto toSaveDto() {
    return DeliverySaveApplicationDto.builder()
        .orderId(this.orderId())
        .supplierCompanyId(this.supplierCompanyId())
        .receiveCompanyId(this.receiveCompanyId())
        .build();
  }
}
