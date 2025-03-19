package com.faster.hub.app.hub.presentation;

import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.response.GetPathsResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/hubs")
public class HubInternalController {

  private final HubService hubService;

  @GetMapping("/paths")
  public ResponseEntity<ApiResponse<GetPathsResponseDto>> getPaths(
      @RequestParam(name = "source_hub_id", required = false) UUID sourceHubId,
      @RequestParam(name = "destination_hub_id", required = false) UUID destinationHubId) {
    return ResponseEntity.ok(
        ApiResponse.ok(GetPathsResponseDto.from(
            hubService.getPaths(GetPathApplicationRequestDto.of(sourceHubId, destinationHubId)))));
  }

}
