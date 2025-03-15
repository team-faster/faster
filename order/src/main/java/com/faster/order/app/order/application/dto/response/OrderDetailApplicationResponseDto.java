package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderDetailApplicationResponseDto(
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
  List<OrderItemDetailApplicationResponseDto> orderItems,
  OrdererInfoDetailApplicationResponseDto ordererInfo
) {

  public static OrderDetailApplicationResponseDto from(Order order) {

    return OrderDetailApplicationResponseDto.builder()
        .id(order.getId())
        .supplierCompanyId(order.getSupplierCompanyId())
        .receivingCompanyId(order.getReceivingCompanyId())
        .deliveryId(order.getDeliveryId())
        .supplierCompanyName(order.getSupplierCompanyName())
        .name(order.getName())
        .totalPrice(order.getTotalPrice())
        .request(order.getRequest())
        .status(order.getStatus())
        .createdAt(order.getCreatedAt())
        .ordererInfo(OrdererInfoDetailApplicationResponseDto.builder()
            .id(order.getOrdererInfo().getId())
            .receivingCompanyName(order.getOrdererInfo().getReceivingCompanyName())
            .receivingCompanyAddress(order.getOrdererInfo().getReceivingCompanyAddress())
            .receivingCompanyContact(order.getOrdererInfo().getReceivingCompanyContact())
            .build()
        )
        .orderItems(order.getOrderItems()
            .stream()
            .map(item -> OrderItemDetailApplicationResponseDto.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build())
            .toList()
        )
        .build();
  }
}
