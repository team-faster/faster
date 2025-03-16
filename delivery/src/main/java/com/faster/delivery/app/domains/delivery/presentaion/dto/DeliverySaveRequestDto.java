package com.faster.delivery.app.domains.delivery.presentaion.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySaveRequestDto(
    UUID orderId,
    UUID sourceHubId,
    UUID destinationHubId,
    UUID receiveCompanyId) {

}
