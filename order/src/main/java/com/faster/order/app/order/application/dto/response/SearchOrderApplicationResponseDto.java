package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.projection.SearchOrderProjection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchOrderApplicationResponseDto(
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

  public static SearchOrderApplicationResponseDto from(SearchOrderProjection querydslDto) {
    return SearchOrderApplicationResponseDto.builder()
        .orderId(querydslDto.id())
        .supplierCompanyId(querydslDto.supplierCompanyId())
        .supplierCompanyName(querydslDto.supplierCompanyName())
        .receivingCompanyId(querydslDto.receivingCompanyId())
        .receivingCompanyName(querydslDto.receivingCompanyName())
        .deliveryId(querydslDto.deliveryId())
        .name(querydslDto.name())
        .totalPrice(querydslDto.totalPrice())
        .status(querydslDto.status())
        .createdAt(querydslDto.createdAt())
        .updatedAt(querydslDto.updatedAt())
        .build();
  }
}
