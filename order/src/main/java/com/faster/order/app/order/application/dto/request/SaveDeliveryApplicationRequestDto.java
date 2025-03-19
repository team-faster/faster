package com.faster.order.app.order.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveDeliveryApplicationRequestDto(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiveCompanyId
) {

  public static SaveDeliveryApplicationRequestDto of(
      UUID orderId, UUID supplierCompanyId, UUID receiveCompanyId) {

    return SaveDeliveryApplicationRequestDto.builder()
        .orderId(orderId)
        .supplierCompanyId(supplierCompanyId)
        .receiveCompanyId(receiveCompanyId)
        .build();
  }
}
