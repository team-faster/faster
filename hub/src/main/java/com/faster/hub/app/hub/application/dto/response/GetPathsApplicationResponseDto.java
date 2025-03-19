package com.faster.hub.app.hub.application.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

public record GetPathsApplicationResponseDto (
  List<Path> paths
){

    public static GetPathsApplicationResponseDto of(List<Path> paths) {
        return new GetPathsApplicationResponseDto(paths);
    }

    @Builder
    public record Path(
        UUID sourceHubId,
        UUID destinationHubId,
        Long distanceMeters,
        Long durationMinutes
    ){

        public static Path of(
            UUID sourceHubId,
            UUID destinationHubId,
            Long distanceMeters,
            Long durationMinutes
        ) {
            return Path.builder()
                .sourceHubId(sourceHubId)
                .destinationHubId(destinationHubId)
                .distanceMeters(distanceMeters)
                .durationMinutes(durationMinutes)
                .build();
        }
    }
}
