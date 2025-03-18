package com.faster.hub.app.hub.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateHubApplicationRequestDto(
    UUID id,
    String name,
    String address,
    String latitude,
    String longitude
) {
}