package com.faster.user.app.user.presentation.internal;

import com.common.response.ApiResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.facade.UserFacade;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.response.PUpdateUserRoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/users")
@RestController
public class InternalUserController {

  private final UserFacade userFacade;

  @PatchMapping("/{userId}/role")
  public ResponseEntity<ApiResponse<PUpdateUserRoleResponseDto>> updateUserRoleByUserId(
      @PathVariable(name = "userId") Long userId,
      @RequestBody PUpdateUserRoleRequestDto requestDto) {
    PUpdateUserRoleResponseDto responseDto =
        userFacade.updateUserRoleByUserId(userId, requestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            responseDto
        ));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<PGetUserResponseDto>> getUserById(
      @PathVariable(name = "userId") Long userId) {

    PGetUserResponseDto responseDto = userFacade.getUserById(userId);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }

  @GetMapping("/{userId}/slack-id")
  public ResponseEntity<ApiResponse<PGetUserSlackIdResponseDto>> getUserSlackIdByUserId(
      @PathVariable(name = "userId") Long userId) {
    PGetUserSlackIdResponseDto responseDto = userFacade.getUserSlackIdByUserId(userId);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }
}
