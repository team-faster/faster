package com.faster.message.app.message.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PSaveMessageResponseDto(
    UUID deliveryId, // 배송 고유 식별 번호
    UUID orderId, // 주문자 정보
    String orderUserName, // 주문자 이름
    String orderUserSlackId, // 주문자 슬랙 ID
    String orderRequestDetails, // 요청사항

    String messageContent, // 메시지 내용
    LocalDateTime messageSendAt, // 메시지 발송 일자
    String messageType, // 메시지 타입

    String productName, // 제품 이름
    Integer productQuantity, // 제품 수량

    String hubSourceName, // 출발지
    String hubWaypointName, // 경유지
    String hubDestinationName, // 목적지

    String deliveryManagerName, // 배송 담당자
    String deliveryManagerSlackId // 배송 담당자 슬랙 ID
) {

}

