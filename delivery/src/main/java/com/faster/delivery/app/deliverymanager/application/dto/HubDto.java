package com.faster.delivery.app.deliverymanager.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record HubDto(
    UUID hubId,
    Long hubManagerId,
    String name,
    String address,
    String latitude,
    String longitude
) {
}
