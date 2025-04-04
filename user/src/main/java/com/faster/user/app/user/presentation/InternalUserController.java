package com.faster.user.app.user.presentation;

import com.common.response.ApiResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.request.AUpdateUserRoleRequestDto;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserSlackIdResponseDto;
import com.faster.user.app.user.application.dto.response.AUpdateUserRoleResponseDto;
import com.faster.user.app.user.facade.UserFacade;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.response.PUpdateUserRoleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원(Internal)", description = "회원 조회 및 수정")
@RequiredArgsConstructor
@RequestMapping("/internal/users")
@RestController
public class InternalUserController {

  private final UserFacade userFacade;

  @Operation(summary = "회원 권한 수정", description = "회원 권한 수정 API 입니다.")
  @PatchMapping("/{userId}/role")
  public ResponseEntity<ApiResponse<PUpdateUserRoleResponseDto>> updateUserRoleByUserId(
      @PathVariable(name = "userId") Long userId,
      @RequestBody PUpdateUserRoleRequestDto requestDto) {

    // presentation DTO  → application DTO
    AUpdateUserRoleRequestDto applicationDto = AUpdateUserRoleRequestDto.from(requestDto);

    // Service
    AUpdateUserRoleResponseDto applicationResponse = userFacade.updateUserRoleByUserId(userId, applicationDto);

    // application DTO  → presentation DTO
    PUpdateUserRoleResponseDto responseDto = PUpdateUserRoleResponseDto.from(applicationResponse);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            responseDto
        ));
  }

  @Operation(summary = "단 건 회원 조회", description = "단 건 회원 조회 API 입니다.")
  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<PGetUserResponseDto>> getUserById(
      @PathVariable(name = "userId") Long userId) {
    // application DTO
    AGetUserResponseDto applicationResponse = userFacade.getUserById(userId);

    //application DTO →  presentation DTO
    PGetUserResponseDto responseDto = PGetUserResponseDto.from(applicationResponse);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }

  @Operation(summary = "회원 슬랙 ID 조회", description = "회원 슬랙 ID 조회 API 입니다.")
  @GetMapping("/{userId}/slack-id")
  public ResponseEntity<ApiResponse<PGetUserSlackIdResponseDto>> getUserSlackIdByUserId(
      @PathVariable(name = "userId") Long userId) {

    AGetUserSlackIdResponseDto applicationResponse = userFacade.getUserSlackIdByUserId(userId);
    PGetUserSlackIdResponseDto responseDto = PGetUserSlackIdResponseDto.from(applicationResponse);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }
}
