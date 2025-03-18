package com.faster.user.app.user.presentation.internal;

import com.common.response.ApiResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.presentation.dto.GetUserResponseDto;
import com.faster.user.app.user.presentation.dto.GetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
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

  private final UserService userService;

  @PatchMapping("/{userId}/role")
  public ResponseEntity<ApiResponse<UpdateUserRoleResponseDto>> updateInternalUserRole(
      @PathVariable(name="userId") Long userId,
      @RequestBody UpdateUserRoleRequestDto requestDto) {
    UpdateUserRoleResponseDto responseDto = userService.updateUserRoleByUserId(userId, requestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            responseDto
        ));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<GetUserResponseDto>> getInternalUser(
      @PathVariable(name="userId") Long userId) {
    GetUserResponseDto responseDto = userService.getUser(userId);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }

  @GetMapping("/{userId}/slack-id")
  public ResponseEntity<ApiResponse<GetUserSlackIdResponseDto>> getInternalUserSlackIdByUserId(
      @PathVariable(name="userId") Long userId) {
    GetUserSlackIdResponseDto responseDto = userService.getInternalUserSlackIdByUserId(userId);

    return ResponseEntity
        .status(UserResponseCode.USER_FOUND.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_FOUND.getMessage(),
            UserResponseCode.USER_FOUND.getStatus().value(),
            responseDto
        ));
  }



}
