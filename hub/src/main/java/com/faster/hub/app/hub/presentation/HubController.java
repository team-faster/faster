package com.faster.hub.app.hub.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.hub.app.hub.application.usecase.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.HubService;
import com.faster.hub.app.hub.application.usecase.dto.request.GetHubsApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import com.faster.hub.app.hub.presentation.dto.response.GetHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.request.SaveHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.response.GetHubsResponseDto;
import com.faster.hub.app.hub.presentation.dto.response.SaveHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.request.UpdateHubRequestDto;
import com.faster.hub.app.hub.presentation.dto.response.UpdateHubResponseDto;
import com.faster.hub.app.hub.presentation.dto.response.UpdateHubRoutesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "허브", description = "허브 생성 및 수정")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hubs")
public class HubController {

  private final HubService hubService;

  @Operation(summary = "허브 저장", description = "허브 저장 API 입니다.")
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

  @Operation(summary = "허브 조회", description = "허브 조회 API 입니다.")
  @GetMapping("/{hubId}")
  public ResponseEntity<ApiResponse<GetHubResponseDto>> getHub(@PathVariable UUID hubId) {
    return ResponseEntity.ok(ApiResponse.ok(GetHubResponseDto.from(hubService.getHub(hubId))));
  }

  @Operation(summary = "모든 허브 조회", description = "모든 허브 조회 API 입니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<GetHubsResponseDto>>> getHubs(
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(name = "search-text", required = false) String searchText,
      @RequestParam(name = "name-search-text", required = false) String nameSearchText,
      @RequestParam(name = "address-search-text", required = false) String addressSearchText
  ){
    PageResponse<GetHubsApplicationResponseDto> hubs =
        hubService.getHubs(GetHubsApplicationRequestDto.of(
            pageable, searchText, nameSearchText, addressSearchText));

    return ResponseEntity.ok(ApiResponse.ok(hubs.map(GetHubsResponseDto::from)));
  }

  @Operation(summary = "허브 수정", description = "허브 수정 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PatchMapping("/{hubId}")
  public ResponseEntity<ApiResponse<UpdateHubResponseDto>> updateHub(
      @PathVariable UUID hubId, @Valid @RequestBody UpdateHubRequestDto updateHubRequestDto) {
    return ResponseEntity.ok(
        ApiResponse.ok(UpdateHubResponseDto.from(
            hubService.updateHub(
                updateHubRequestDto.toUpdateHubApplicationRequestDto(hubId)))));
  }

  @Operation(summary = "허브 경로 수정", description = "허브 경로 수정 API 입니다.")
  @PatchMapping("/hub-routes")
  public ResponseEntity<ApiResponse<UpdateHubRoutesResponseDto>> updateHubRoutes(){
    return ResponseEntity.ok(
        ApiResponse.ok(UpdateHubRoutesResponseDto.from(hubService.updateHubRoutes())));
  }

  @Operation(summary = "허브 삭제", description = "허브 삭제 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @DeleteMapping("/{hubId}")
  public ResponseEntity<ApiResponse<UUID>> deleteHub(
      @CurrentUserInfo CurrentUserInfoDto userInfo, @PathVariable UUID hubId) {
    hubService.deleteHub(
        DeleteHubApplicationRequestDto.of(hubId, userInfo.userId(), LocalDateTime.now()));
    return ResponseEntity.ok(ApiResponse.of(HttpStatus.NO_CONTENT, hubId));
  }

}
