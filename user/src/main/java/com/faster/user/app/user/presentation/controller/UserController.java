package com.faster.user.app.user.presentation.controller;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import com.faster.user.app.user.presentation.dto.GetAllUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}