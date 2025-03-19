package com.faster.delivery.app.delivery.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryUpdateDto(
    UUID companyDeliveryManagerId,
    String status
) {

}
