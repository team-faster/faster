package com.faster.message.app.message.presentation.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PSaveMessageRequestDto(
    UUID deliveryId, // 배송 ID
    UUID hubSourceId, // 허브 출발지 ID
    UUID receiveHubId, // 허브 도착지 ID
    String hubSourceName, // 허브 출발지 이름
    String hubWaypointName, // 허브 경유지 이름
    String hubDestinationName, // 허브 도착지 이름
    OrderInfo orderInfo, // 주문 정보
    List<DeliveryManagerInfo> deliveryManagers // 배달 담당자 정보
) {
  public record OrderInfo(
      UUID orderId, // 주문 고유 식별 번호
      String orderUserName, // 주문 회원 이름
      String orderUserSlackId // 주문 회원 슬랙 아이디
  ) {
  }

  public record DeliveryManagerInfo(
      Long deliveryManagerId, // 배송 담당자의 회원 ID
      String deliveryManagerType, // 업체 ? 허브 ? 배송 담당자 유무
      String deliveryManagerName // 배송 담당자 이름
  ) {
  }
}