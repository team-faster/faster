package com.faster.order.app.order.infrastructure.feign.dto.request;

import com.faster.order.app.order.application.dto.request.SaveDeliveryApplicationRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveDeliveryRequestDto(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiveCompanyId
) {

  public static SaveDeliveryRequestDto from(SaveDeliveryApplicationRequestDto requestDto) {
    return SaveDeliveryRequestDto.builder()
        .orderId(requestDto.orderId())
        .supplierCompanyId(requestDto.supplierCompanyId())
        .receiveCompanyId(requestDto.receiveCompanyId())
        .build();
  }
}
