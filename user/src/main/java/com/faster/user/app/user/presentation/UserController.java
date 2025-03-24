package com.faster.user.app.user.presentation;

import static com.common.resolver.dto.UserRole.ROLE_COMPANY;
import static com.common.resolver.dto.UserRole.ROLE_DELIVERY;
import static com.common.resolver.dto.UserRole.ROLE_HUB;
import static com.common.resolver.dto.UserRole.ROLE_MASTER;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import com.faster.user.app.user.facade.UserFacade;
import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserPasswordRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.presentation.dto.response.PDeleteUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원(External)", description = "회원 조회 및 수정")
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserFacade userFacade;

  @Operation(summary = "모든 회원 조회", description = "모든 회원 조회 API 입니다.")
  @AuthCheck(roles = {ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<PGetAllUserResponseDto>>> getAllUsers(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String slackId,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {

    PageResponse<AGetAllUserResponseDto> applicationResponse = userFacade.getAllUsers(username, name, slackId, page,
        size);

    // application DTO → presentation DTO
    PageResponse<PGetAllUserResponseDto> response = applicationResponse.map(PGetAllUserResponseDto::from);

    return ResponseEntity.ok(new ApiResponse<>(
        UserResponseCode.USER_FOUND.getMessage(),
        UserResponseCode.USER_FOUND.getStatus().value(),
        response
    ));
  }

  @Operation(summary = "회원 삭제", description = "회원 삭제 API 입니다.")
  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse<PDeleteUserResponseDto>> deleteUserRole(
      @PathVariable(name = "userId") Long userId,
      @RequestBody PDeleteUserRequestDto requestDto,
      @CurrentUserInfo CurrentUserInfoDto userInfo) {

    // presentation DTO → Application DTO
    ADeleteUserRequestDto applicationDto = ADeleteUserRequestDto.from(requestDto);

    ADeleteUserResponseDto applicationResponse = userFacade.deleteUserByUserId(userId, applicationDto);

    // application DTO → presentation DTO
    PDeleteUserResponseDto responseDto = PDeleteUserResponseDto.from(applicationResponse);

    return ResponseEntity
        .status(UserResponseCode.USER_SOFT_DELETED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_SOFT_DELETED.getMessage(),
            UserResponseCode.USER_SOFT_DELETED.getStatus().value(),
            responseDto
        ));
  }

  @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호 수정 API 입니다.")
  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @PatchMapping("/{userId}/password")
  public ResponseEntity<ApiResponse<Void>> updateUserPassword(
      @PathVariable(name = "userId") Long userId,
      @RequestBody PUpdateUserPasswordRequestDto requestDto,
      @CurrentUserInfo CurrentUserInfoDto userInfo) {
    AUpdateUserPasswordRequestDto aRequestDto = PUpdateUserPasswordRequestDto.fromDto(requestDto);

    userFacade.updateUserPassword(userId, aRequestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            null
        ));
  }

  @Operation(summary = "회원 슬랙 ID 수정", description = "회원 슬랙 ID 수정 API 입니다.")
  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @PatchMapping("/{userId}/slack-id")
  public ResponseEntity<ApiResponse<Void>> updateUserSlackId(
      @PathVariable(name = "userId") Long userId,
      @RequestBody PUpdateUserSlackIdRequestDto requestDto,
      @CurrentUserInfo CurrentUserInfoDto userInfo) {
    AUpdateUserSlackIdRequestDto aRequestDto = PUpdateUserSlackIdRequestDto.from(requestDto);

    userFacade.updateUserSlackId(userId, aRequestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            null
        ));
  }

  @Operation(summary = "나의 회원 정보 조회", description = "나의 회원 정보 조회 API 입니다.")
  @AuthCheck(roles = {ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_DELIVERY})
  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<PGetUserResponseDto>> getMyUserById(
      @PathVariable(name = "userId") Long userId,
      @CurrentUserInfo CurrentUserInfoDto userInfo
  ) {
    // application DTO
    AGetUserResponseDto applicationResponse = userFacade.getMyUserById(userId, userInfo);

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
}