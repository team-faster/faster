package com.faster.delivery.app.delivery.infrastructure.feign.dto.hub;

import java.util.UUID;
import lombok.Builder;

@Builder
public record HubPathRequestDto(
    UUID sourceHubId,
    UUID destinationHubId
) {
}
