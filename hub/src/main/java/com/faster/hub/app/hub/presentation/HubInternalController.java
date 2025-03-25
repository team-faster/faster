package com.faster.hub.app.hub.presentation;

import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.usecase.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.response.GetHubsInternalResponseDto;
import com.faster.hub.app.hub.presentation.dto.response.GetPathsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "허브 경로", description = "허브 경로 생성 및 수정")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/hubs")
public class HubInternalController {

  private final HubService hubService;

  @Operation(summary = "허브 경로 조회(출발지|목적지)", description = "허브 경로 조회(출발지|목적지) API 입니다.")
  @GetMapping("/paths")
  public ResponseEntity<ApiResponse<GetPathsResponseDto>> getPaths(
      @RequestParam(name = "source-hub-id", required = false) UUID sourceHubId,
      @RequestParam(name = "destination-hub-id", required = false) UUID destinationHubId) {
    return ResponseEntity.ok(
        ApiResponse.ok(GetPathsResponseDto.from(
            hubService.getPaths(GetPathApplicationRequestDto.of(sourceHubId, destinationHubId)))));
  }

  @Operation(summary = "허브 경로 조회(허브 ID|허브 담당자 ID)", description = "허브 경로 조회(허브 ID|허브 담당자 ID) API 입니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<GetHubsInternalResponseDto>> getHubs(
      @RequestParam(name = "hubs", required = false) List<UUID> hubIds,
      @RequestParam(name = "hub-manager-id", required = false) Long hubManagerId
  ){
    return ResponseEntity.ok(ApiResponse.ok(
        GetHubsInternalResponseDto.from(hubService.getHubsInternal(hubIds, hubManagerId))));
  }

}
