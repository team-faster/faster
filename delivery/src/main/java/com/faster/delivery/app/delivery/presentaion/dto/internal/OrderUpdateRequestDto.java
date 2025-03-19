package com.faster.delivery.app.delivery.presentaion.dto.internal;

import lombok.Builder;

@Builder
public record OrderUpdateRequestDto(
	String status
) {
}
