package com.faster.delivery.app.delivery.application.dto;

import lombok.Builder;

@Builder
public record DeliveryUpdateDto(
    Long companyDeliveryManagerId,
    String status
) {

}
