package com.faster.hub.app.hub.presentation.dto.response;

import com.faster.hub.app.hub.application.dto.response.GetPathsApplicationResponseDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record GetPathsResponseDto(
    List<Path> paths
){

  public static GetPathsResponseDto from(GetPathsApplicationResponseDto dto) {
    return GetPathsResponseDto.builder().paths(
        dto.paths().stream().map(Path::from).collect(Collectors.toList())
    ).build();
  }

  @Builder
  private record Path(
      UUID sourceHubId,
      UUID destinationHubId,
      Long distanceM,
      Long durationMin
  ){
    private static Path from(GetPathsApplicationResponseDto.Path path){
      return Path.builder()
          .sourceHubId(path.sourceHubId())
          .destinationHubId(path.destinationHubId())
          .distanceM(path.distanceMeters())
          .durationMin(path.durationMinutes())
          .build();
    }
  }
}
