package com.faster.order.app.order.presentation.dto.response;

import com.faster.order.app.order.application.dto.response.IGetOrderDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.IGetOrderDetailApplicationResponseDto.OrderItemDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.IGetOrderDetailApplicationResponseDto.OrdererInfoDetailApplicationResponseDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record IGetOrderDetailResponseDto(
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
  LocalDateTime updatedAt,
  OrdererInfoDetailResponseDto ordererInfo,
  List<OrderItemDetailResponseDto> orderItems
) {

  public static IGetOrderDetailResponseDto from(IGetOrderDetailApplicationResponseDto dto) {

    return IGetOrderDetailResponseDto.builder()
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
        .updatedAt(dto.updatedAt())
        .ordererInfo(OrdererInfoDetailResponseDto.from(dto.ordererInfo()))
        .orderItems(dto.orderItems()
            .stream()
            .map(OrderItemDetailResponseDto::from)
            .toList()
        )
        .build();
  }

  @Builder
  public record OrdererInfoDetailResponseDto(
      UUID id,
      String receivingCompanyName,
      String receivingCompanyAddress,
      String receivingCompanyContact
  ) {

    public static OrdererInfoDetailResponseDto from(
        OrdererInfoDetailApplicationResponseDto applicationResponseDto) {

      return OrdererInfoDetailResponseDto.builder()
          .id(applicationResponseDto.id())
          .receivingCompanyName(applicationResponseDto.receivingCompanyName())
          .receivingCompanyAddress(applicationResponseDto.receivingCompanyAddress())
          .receivingCompanyContact(applicationResponseDto.receivingCompanyContact())
          .build();
    }
  }

  @Builder
  public record OrderItemDetailResponseDto(
      UUID id,
      UUID productId,
      String name,
      Integer quantity,
      BigDecimal price
  ) {
    public static OrderItemDetailResponseDto from(
        OrderItemDetailApplicationResponseDto applicationResponseDto) {
      return OrderItemDetailResponseDto.builder()
          .id(applicationResponseDto.id())
          .productId(applicationResponseDto.productId())
          .name(applicationResponseDto.name())
          .quantity(applicationResponseDto.quantity())
          .price(applicationResponseDto.price())
          .build();
    }
  }
}
