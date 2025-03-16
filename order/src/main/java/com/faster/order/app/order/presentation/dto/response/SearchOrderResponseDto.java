package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchOrderResponseDto(
    UUID orderId,
    UUID supplierCompanyId,
    String supplierCompanyName,
    UUID receivingCompanyId,
    String receivingCompanyName,
    UUID deliveryId,
    String name,
    BigDecimal totalPrice,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static SearchOrderResponseDto from(SearchOrderApplicationResponseDto applicationResponse) {

    return SearchOrderResponseDto.builder()
        .orderId(applicationResponse.orderId())
        .supplierCompanyId(applicationResponse.supplierCompanyId())
        .supplierCompanyName(applicationResponse.supplierCompanyName())
        .receivingCompanyId(applicationResponse.receivingCompanyId())
        .receivingCompanyName(applicationResponse.receivingCompanyName())
        .deliveryId(applicationResponse.deliveryId())
        .name(applicationResponse.name())
        .totalPrice(applicationResponse.totalPrice())
        .status(applicationResponse.status())
        .createdAt(applicationResponse.createdAt())
        .updatedAt(applicationResponse.updatedAt())
        .build();
  }
}
