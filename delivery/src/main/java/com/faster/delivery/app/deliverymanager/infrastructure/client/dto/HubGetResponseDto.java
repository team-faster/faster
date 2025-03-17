package com.faster.delivery.app.deliverymanager.infrastructure.client.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record HubGetResponseDto(
    UUID hubId,
    String name,
    String address,
    String latitude,
    String longitude
) {

}
