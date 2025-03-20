package com.faster.delivery.app.delivery.infrastructure.client.dto.order;

import lombok.Builder;

@Builder
public record OrderUpdateRequestDto(
	String status
) {
}
