package com.faster.hub.app.hub.application.usecase.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateHubApplicationRequestDto(
    UUID id,
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude
) {
}