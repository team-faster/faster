package com.faster.hub.app.hub.presentation;

import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.usecase.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.response.GetHubsInternalResponseDto;
import com.faster.hub.app.hub.presentation.dto.response.GetPathsResponseDto;
import java.util.List;
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
      @RequestParam(name = "source-hub-id", required = false) UUID sourceHubId,
      @RequestParam(name = "destination-hub-id", required = false) UUID destinationHubId) {
    return ResponseEntity.ok(
        ApiResponse.ok(GetPathsResponseDto.from(
            hubService.getPaths(GetPathApplicationRequestDto.of(sourceHubId, destinationHubId)))));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<GetHubsInternalResponseDto>> getHubs(
      @RequestParam(name = "hubs", required = false) List<UUID> hubIds,
      @RequestParam(name = "hub-manager-id", required = false) Long hubManagerId
  ){
    return ResponseEntity.ok(ApiResponse.ok(
        GetHubsInternalResponseDto.from(hubService.getHubsInternal(hubIds, hubManagerId))));
  }

}
