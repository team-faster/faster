package com.faster.hub.app.hub.presentation;

import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.CreateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.CreateHubResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ApiResponse> createHub(@CurrentUserInfo CurrentUserInfoDto currentUserInfoDto,
      @Valid @RequestBody CreateHubRequestDto hubRequestDto) {
    CreateHubResponseDto hubResponseDto = CreateHubResponseDto.from(
        hubService.createHub(CreateHubApplicationRequestDto.from(hubRequestDto)));

    return ResponseEntity.created(
        UriComponentsBuilder.fromUriString("/api/hubs/{hub-id}")
            .buildAndExpand(hubResponseDto.id())
            .toUri()
    ).body(ApiResponse.of(HttpStatus.CREATED, hubResponseDto));
  }
}
