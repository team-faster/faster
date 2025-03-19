package com.faster.hub.app.hub.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.hub.app.hub.application.usecase.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.presentation.dto.response.GetHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.request.SaveHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.response.SaveHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.request.UpdateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.response.UpdateHubResponseDto;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<SaveHubResponseDto>> saveHub(
      @Valid @RequestBody SaveHubRequestDto hubRequestDto) {
    SaveHubResponseDto hubResponseDto = SaveHubResponseDto.from(
        hubService.saveHub(hubRequestDto.toSaveHubApplicationRequestDto()));

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

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PatchMapping("/{hubId}")
  public ResponseEntity<ApiResponse<UpdateHubResponseDto>> updateHub(@PathVariable UUID hubId,
      @Valid @RequestBody UpdateHubRequestDto updateHubRequestDto) {
    return ResponseEntity.ok(
        ApiResponse.ok(UpdateHubResponseDto.from(
            hubService.updateHub(
                updateHubRequestDto.toUpdateHubApplicationRequestDto(hubId, updateHubRequestDto)))));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @DeleteMapping("/{hubId}")
  public ResponseEntity<ApiResponse<UUID>> deleteHub(@CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID hubId) {
    hubService.deleteHub(
        DeleteHubApplicationRequestDto.of(hubId, userInfo.userId(), LocalDateTime.now()));
    return ResponseEntity.ok(ApiResponse.of(HttpStatus.NO_CONTENT, hubId));
  }
}
