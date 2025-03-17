package com.faster.user.app.user.presentation.internal;

import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserQuerydslResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/users")
@RestController
public class InternalUserController {

  private final UserService userService;

  @PatchMapping("/{userId}/role")
  public ResponseEntity<ApiResponse<UpdateUserRoleResponseDto>> updateUserRole(
      @PathVariable Long userId,
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


  @GetMapping
  public ResponseEntity<ApiResponse<Page<QUserQuerydslResponseDto>>> getAllUsers(
      Pageable pageable,
      @RequestParam(value = "username", defaultValue = "", required = false) String username,
      @RequestParam(value = "name", defaultValue = "", required = false) String name,
      @RequestParam(value = "slackId", defaultValue = "", required = false) String slackId,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    Page<QUserQuerydslResponseDto> users = userService.getAllUsers(
        username,
        name,
        slackId,
        page,
        size);

    return ResponseEntity
        .status(UserResponseCode.USER_UPDATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_UPDATED.getMessage(),
            UserResponseCode.USER_UPDATED.getStatus().value(),
            users
        ));

  }
}
