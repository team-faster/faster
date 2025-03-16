package com.faster.hub.app.hub.presentation;

import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.UpdateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.CreateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.CreateHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.GetHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.UpdateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.UpdateHubResponseDto;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hubs")
public class HubController {

  private final HubService hubService;

  @PostMapping
  public ResponseEntity<ApiResponse> createHub(
      @Valid @RequestBody CreateHubRequestDto hubRequestDto) {
    CreateHubResponseDto hubResponseDto = CreateHubResponseDto.from(
        hubService.createHub(CreateHubApplicationRequestDto.from(hubRequestDto)));

    return ResponseEntity.created(
        UriComponentsBuilder.fromUriString("/api/hubs/{hub-id}")
            .buildAndExpand(hubResponseDto.id())
            .toUri()
    ).body(ApiResponse.of(HttpStatus.CREATED, hubResponseDto));
  }

  @GetMapping("/{hubId}")
  public ResponseEntity<ApiResponse<GetHubResponseDto>> getHub(@PathVariable UUID hubId) {
    return ResponseEntity.ok(ApiResponse.ok(GetHubResponseDto.from(hubService.getHub(hubId))));
  }

  @PatchMapping("/{hubId}")
  public ResponseEntity<ApiResponse<UpdateHubResponseDto>> updateHub(@PathVariable UUID hubId,
      @Valid @RequestBody UpdateHubRequestDto updateHubRequestDto) {
    return ResponseEntity.ok(
        ApiResponse.ok(UpdateHubResponseDto.from(
            hubService.updateHub(UpdateHubApplicationRequestDto.of(hubId, updateHubRequestDto)))));
  }
}
