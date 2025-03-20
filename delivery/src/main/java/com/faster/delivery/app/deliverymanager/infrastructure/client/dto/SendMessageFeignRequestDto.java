package com.faster.delivery.app.deliverymanager.infrastructure.client.dto;

import com.faster.delivery.app.deliverymanager.application.dto.SendMessageApplicationRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SendMessageFeignRequestDto(
    UUID deliveryId,
    UUID orderId, // 주문자 정보
    String orderUserName, // 주문자 이름
    String orderUserSlackId, // 주문자 슬랙 ID
    String hubSource, // 출발지
    String hubWaypoint, // 경유지
    String hubDestination, // 목적지
    String deliveryManager // 배송 담당자
) {


  public static SendMessageFeignRequestDto from(SendMessageApplicationRequestDto dto) {
    return null;
  }
}