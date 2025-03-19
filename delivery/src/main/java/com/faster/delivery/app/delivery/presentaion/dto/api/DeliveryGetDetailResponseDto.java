package com.faster.delivery.app.delivery.presentaion.dto.api;

import com.faster.delivery.app.delivery.application.dto.DeliveryDetailDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryDetailDto.DeliveryRouteDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record DeliveryGetDetailResponseDto(
    String deliveryId,
    String orderId,
    String companyDeliveryManagerName,
    String status,
    String sourceHubName,
    String destinationHubName,
    String companyName,
    String address,
    String createdAt,
    String updatedAt,
    List<DeliveryRouteResponseDto> deliveryRouteList
) {
  public static DeliveryGetDetailResponseDto from(DeliveryDetailDto deliveryDetailDto) {
    return DeliveryGetDetailResponseDto.builder()
        .deliveryId(deliveryDetailDto.deliveryId())
        .orderId(deliveryDetailDto.orderId())
        .companyDeliveryManagerName(deliveryDetailDto.companyDeliveryManagerName())
        .status(deliveryDetailDto.status())
        .sourceHubName(deliveryDetailDto.sourceHubName())
        .destinationHubName(deliveryDetailDto.destinationHubName())
        .companyName(deliveryDetailDto.companyName())
        .address(deliveryDetailDto.address())
        .createdAt(deliveryDetailDto.createdAt())
        .updatedAt(deliveryDetailDto.updatedAt())
        .deliveryRouteList(DeliveryRouteResponseDto.from(deliveryDetailDto.deliveryRouteList()))
        .build();
  }

  @Builder
  public record DeliveryRouteResponseDto(
      String id,
      String deliveryId,
      String deliveryManagerId,
      String sourceHubId,
      String destinationHubId,
      String deliveryManagerName,
      Integer sequence,
      Long expectedDistanceM,
      Long expectedTimeMin,
      Long realDistanceM,
      Long realTimeMin,
      String type,
      String createdBy,
      String createdAt,
      String updatedBy,
      String updatedAt,
      String status
  ) {
    public static DeliveryRouteResponseDto from(DeliveryDetailDto.DeliveryRouteDto deliveryRoute) {
      return DeliveryRouteResponseDto.builder()
          .id(deliveryRoute.id())
          .deliveryId(deliveryRoute.deliveryId())
          .deliveryManagerId(deliveryRoute.deliveryManagerId())
          .sourceHubId(deliveryRoute.sourceHubId())
          .destinationHubId(deliveryRoute.destinationHubId())
          .deliveryManagerName(deliveryRoute.deliveryManagerName())
          .sequence(deliveryRoute.sequence())
          .expectedDistanceM(deliveryRoute.expectedDistanceM())
          .expectedTimeMin(deliveryRoute.expectedTimeMin())
          .realDistanceM(deliveryRoute.realDistanceM())
          .realTimeMin(deliveryRoute.realTimeMin())
          .type(deliveryRoute.type())
          .createdBy(deliveryRoute.createdBy())
          .createdAt(deliveryRoute.createdAt())
          .updatedBy(deliveryRoute.updatedBy())
          .updatedAt(deliveryRoute.updatedAt())
          .status(deliveryRoute.status())
          .build();
    }

    public static List<DeliveryRouteResponseDto> from(List<DeliveryDetailDto.DeliveryRouteDto> deliveryRouteDtoList) {
      ArrayList<DeliveryRouteResponseDto> deliveryRouteResponseDtoList = new ArrayList<>();
      for (DeliveryRouteDto deliveryRouteDto : deliveryRouteDtoList) {
        deliveryRouteResponseDtoList.add(DeliveryRouteResponseDto.from(deliveryRouteDto));
      }
      return deliveryRouteResponseDtoList;
    }
  }
}
