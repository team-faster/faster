package com.faster.message.app.message.application.dto;

import com.faster.message.app.message.domain.enums.MessageType;
import java.time.LocalDateTime;
import java.util.UUID;

public record SaveGeminiMessageRequestDto(
    UUID orderId, // 주문자 정보
    String name, // 주문자 이름
    String targetSlackId, // 슬랙 ID
    String productName, // 제품 이름
    Integer quantity, // 제품 수량
    String requestDetails, // 요청사항
    String source, // 출발지
    String waypoint, // 경유지
    String destination, // 목적지
    MessageType type, // 메시지 타입
    LocalDateTime sendAt, // 메시지 발송 일자
    String deliveryManager, // 배송 담당자
    String deliveryManagerSlackId // 배송 담당자 슬랙 ID
) {
}
