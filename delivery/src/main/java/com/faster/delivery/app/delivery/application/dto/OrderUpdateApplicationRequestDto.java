package com.faster.delivery.app.delivery.application.dto;

import lombok.Builder;

@Builder
public record OrderUpdateApplicationRequestDto(
	String status
) {

	public static OrderUpdateApplicationRequestDto of(String status) {
		return OrderUpdateApplicationRequestDto.builder()
				.status(status)
				.build();
	}
}
