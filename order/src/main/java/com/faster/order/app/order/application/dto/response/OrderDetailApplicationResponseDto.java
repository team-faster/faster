package com.faster.order.app.order.application.dto.response;

import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.entity.OrderItem;
import com.faster.order.app.order.domain.entity.OrdererInfo;
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
        .ordererInfo(OrdererInfoDetailApplicationResponseDto.from(order.getOrdererInfo()))
        .orderItems(order.getOrderItems()
            .stream()
            .map(OrderItemDetailApplicationResponseDto::from)
            .toList()
        )
        .build();
  }

  @Builder
  public record OrdererInfoDetailApplicationResponseDto(
      UUID id,
      String receivingCompanyName,
      String receivingCompanyAddress,
      String receivingCompanyContact
  ) {

    public static OrdererInfoDetailApplicationResponseDto from(
        OrdererInfo ordererInfo) {
      return OrdererInfoDetailApplicationResponseDto.builder()
          .id(ordererInfo.getId())
          .receivingCompanyName(ordererInfo.getReceivingCompanyName())
          .receivingCompanyAddress(ordererInfo.getReceivingCompanyAddress())
          .receivingCompanyContact(ordererInfo.getReceivingCompanyContact())
          .build();
    }
  }

  @Builder
  public record OrderItemDetailApplicationResponseDto(
      UUID id,
      UUID productId,
      String name,
      Integer quantity,
      BigDecimal price
  ) {

    public static OrderItemDetailApplicationResponseDto from(
        OrderItem orderItem) {
      return OrderItemDetailApplicationResponseDto.builder()
          .id(orderItem.getId())
          .productId(orderItem.getProductId())
          .name(orderItem.getName())
          .quantity(orderItem.getQuantity())
          .price(orderItem.getPrice())
          .build();
    }
  }
}
