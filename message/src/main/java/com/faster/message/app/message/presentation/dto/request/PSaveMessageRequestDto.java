package com.faster.message.app.message.presentation.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PSaveMessageRequestDto(
    UUID deliveryId, // 배송 고유 식별 번호
    UUID orderId, // 주문자 정보
    String orderUserName, // 주문자 이름
    String orderUserSlackId, // 주문자 슬랙 ID

    String hubSource, // 출발지
    String hubWaypoint, // 경유지
    String hubDestination, // 목적지

    String deliveryManager // 배송 담당자
) {


}
