package com.faster.message.app.message.application.dto.response;

import lombok.Builder;

@Builder
public record AGetDeliveryManagerResponseDto(
    Long userId
) {
}
