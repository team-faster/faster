package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderDetailResponseDto(
  UUID id,
  UUID supplierCompanyId,
  UUID receivingCompanyId,
  UUID deliveryId,
  String supplierCompanyName,
  String name,
  BigDecimal totalPrice,
  String request,
  OrderStatus status,
  LocalDateTime createdAt,
  OrdererInfoDetailResponseDto ordererInfo,
  List<OrderItemDetailResponseDto> orderItems
) {

  public static OrderDetailResponseDto from(OrderDetailApplicationResponseDto dto) {

    return OrderDetailResponseDto.builder()
        .id(dto.id())
        .supplierCompanyId(dto.supplierCompanyId())
        .receivingCompanyId(dto.receivingCompanyId())
        .deliveryId(dto.deliveryId())
        .supplierCompanyName(dto.supplierCompanyName())
        .name(dto.name())
        .totalPrice(dto.totalPrice())
        .request(dto.request())
        .status(dto.status())
        .createdAt(dto.createdAt())
        .ordererInfo(
            OrdererInfoDetailResponseDto.builder()
                .id(dto.ordererInfo().id())
                .receivingCompanyName(dto.ordererInfo().receivingCompanyName())
                .receivingCompanyAddress(dto.ordererInfo().receivingCompanyAddress())
                .receivingCompanyContact(dto.ordererInfo().receivingCompanyContact())
                .build()
        )
        .orderItems(dto.orderItems()
            .stream()
            .map(item -> OrderItemDetailResponseDto.builder()
                .id(item.id())
                .productId(item.productId())
                .name(item.name())
                .quantity(item.quantity())
                .price(item.price())
                .build())
            .toList()
        )
        .build();
  }
}
