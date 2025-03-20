package com.faster.message.app.message.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ASaveMessageRequestDto(
    UUID deliveryId, // 배송 고유 식별 번호
    UUID orderId, // 주문 정보
    String orderUserName, // 주문자 이름
    String orderUserSlackId, // 주문자 Slack ID

    String hubSource, // 출발지
    String hubWaypoint, // 경유지
    String hubDestination, // 목적지

    String deliveryManager // 배송 담당자
) {
}
