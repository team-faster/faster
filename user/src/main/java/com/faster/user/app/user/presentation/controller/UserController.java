package com.faster.user.app.user.presentation.controller;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.user.app.user.presentation.dto.DeleteUserResponseDto;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.dto.DeleteUserRequestDto;
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.presentation.dto.GetAllUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserService userService;

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<GetAllUserResponseDto>>> getAllUsers(
      @RequestParam(value = "username", defaultValue = "", required = false) String username,
      @RequestParam(value = "name", defaultValue = "", required = false) String name,
      @RequestParam(value = "slackId", defaultValue = "", required = false) String slackId,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    PageResponse<GetAllUserResponseDto> users = userService.getAllUsers(
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


  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse<DeleteUserResponseDto>> updateUserRole(@PathVariable(name = "userId") Long userId,
                                                                           @RequestBody DeleteUserRequestDto requestDto) {
    DeleteUserResponseDto deletedUserByUserId = userService.deleteUserByUserId(userId, requestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_SOFT_DELETED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_SOFT_DELETED.getMessage(),
            UserResponseCode.USER_SOFT_DELETED.getStatus().value(),
            deletedUserByUserId
        ));
  }


}