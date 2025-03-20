package com.faster.message.app.message.infrastructure.fegin.dto.response;

public record IGetDeliveryManagerResponseDto(
    Long userId // 회원 고유 식별 번호 -> 이름, Slack ID
) {
}
