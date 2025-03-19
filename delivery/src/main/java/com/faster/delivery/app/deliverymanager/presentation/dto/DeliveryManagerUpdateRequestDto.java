package com.faster.delivery.app.deliverymanager.presentation.dto;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import lombok.Builder;

@Builder
public record DeliveryManagerUpdateRequestDto(
	String type,
	Integer deliverySequenceNumber
) {

	public DeliveryManagerUpdateDto toUpdateDto() {
		return DeliveryManagerUpdateDto.builder()
				.type(this.type)
				.deliverySequenceNumber(this.deliverySequenceNumber)
				.build();
	}
}
