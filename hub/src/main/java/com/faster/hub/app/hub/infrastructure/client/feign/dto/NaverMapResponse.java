package com.faster.hub.app.hub.infrastructure.client.feign.dto;

import com.faster.hub.app.hub.application.usecase.dto.response.DirectionsApiApplicationResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public record NaverMapResponse (
    int code,
    String message,
    LocalDateTime currentDateTime,
    Route route
) {

  public DirectionsApiApplicationResponseDto toApplicationDto() {
    if(route == null) return null;

    List<TraOptimal> traoptimal = this.route().traoptimal();
    if(traoptimal.isEmpty()) return null;

    Summary summary = traoptimal.get(0).summary();
    return DirectionsApiApplicationResponseDto.builder()
        .distanceMiters(summary.distance)
        .durationMinutes(summary.duration / 60_000)
        .build();
  }

  public record Route(
      List<TraOptimal> traoptimal
  ) { }

  public record TraOptimal(
      Summary summary,
      List<List<Double>> path,
      List<Section> section,
      List<Guide> guide
  ) { }

  public record Summary(
      Location start,
      Goal goal,
      long distance,
      long duration,
      LocalDateTime departureTime,
      List<List<Double>> bbox,
      int tollFare,
      int taxiFare,
      int fuelPrice
  ) { }

  public record Location(
      List<Double> location
  ) {}

  public record Goal(
      List<Double> location,
      int dir
  ) {}

  public record Section(
      int pointIndex,
      int pointCount,
      int distance,
      String name,
      int congestion,
      int speed
  ) {}

  public record Guide(
      int pointIndex,
      int type,
      String instructions,
      int distance,
      long duration
  ) {}
}


