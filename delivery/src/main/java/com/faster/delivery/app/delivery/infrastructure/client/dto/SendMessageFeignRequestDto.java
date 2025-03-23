package com.faster.delivery.app.delivery.infrastructure.client.dto;

import com.faster.delivery.app.delivery.application.dto.SendMessageApplicationRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SendMessageFeignRequestDto(
    UUID deliveryId, // 배송 ID
    UUID hubSourceId, // 허브 출발지 ID
    UUID receiveHubId, // 허브 도착지 ID
    String hubSourceName, // 허브 출발지 이름
    String hubWaypointName, // 허브 경유지 이름
    String hubDestinationName, // 허브 도착지 이름
    OrderInfo orderInfo, // 주문 정보
    List<DeliveryManagerInfo> deliveryManagers // 배달 담당자 정보
) {


  public static SendMessageFeignRequestDto from(SendMessageApplicationRequestDto dto) {
    return SendMessageFeignRequestDto.builder()
        .deliveryId(dto.deliveryId())
        .hubSourceId(dto.hubSourceId())
        .receiveHubId(dto.receiveHubId())
        .hubSourceName(dto.hubSourceName())
        .hubWaypointName(dto.hubWaypointName())
        .hubDestinationName(dto.hubDestinationName())
        .orderInfo(OrderInfo.from(dto.orderInfo()))
        .deliveryManagers(dto.deliveryManagers().stream().map(DeliveryManagerInfo::from).toList())
        .build();
  }

  @Builder
  public record OrderInfo(
      UUID orderId, // 주문 고유 식별 번호
      String orderUserName, // 주문 회원 이름
      String orderUserSlackId // 주문 회원 슬랙 아이디
  ) {
    public static OrderInfo from(SendMessageApplicationRequestDto.OrderInfo orderInfo){
      return OrderInfo.builder()
          .orderId(orderInfo.orderId())
          .orderUserName(orderInfo.orderUserName())
          .orderUserSlackId(orderInfo.orderUserSlackId())
          .build();
    }
  }

  @Builder
  public record DeliveryManagerInfo(
      Long deliveryManagerId, // -> deliveryManagerId // 배송 담당자 ID
      String deliveryManagerType, // 업체 ? 허브 ? 배송 담당자 유무
      String deliveryManagerName // 배송 담당자 이름
  ) {
    public static DeliveryManagerInfo from(SendMessageApplicationRequestDto.DeliveryManagerInfo info){
      return DeliveryManagerInfo.builder()
          .deliveryManagerId(info.deliveryManagerId())
          .deliveryManagerType(info.deliveryManagerType())
          .deliveryManagerName(info.deliveryManagerName())
          .build();
    }
  }
}