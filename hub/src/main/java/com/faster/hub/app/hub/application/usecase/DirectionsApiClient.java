package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.usecase.dto.response.DirectionsApiApplicationResponseDto;

public interface DirectionsApiClient {

  DirectionsApiApplicationResponseDto getDrivingRoute(String start, String goal);
}
