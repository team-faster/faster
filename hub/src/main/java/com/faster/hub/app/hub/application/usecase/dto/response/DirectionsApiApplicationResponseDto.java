package com.faster.hub.app.hub.application.usecase.dto.response;

import lombok.Builder;

@Builder
public record DirectionsApiApplicationResponseDto (
    Long distanceMiters,
    Long durationMinutes
) {
}
