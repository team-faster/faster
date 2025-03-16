package com.faster.user.app.auth.presentation.controller;

import com.common.response.ApiResponse;
import com.faster.user.app.auth.application.dto.CreateUserRequestDto;
import com.faster.user.app.auth.application.usecase.AuthUseCase;
import com.faster.user.app.global.response.enums.UserResponseCode;
import com.faster.user.app.user.presentation.dto.CreateUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthUseCase authUseCase;


  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<CreateUserResponseDto>> createUser(@RequestBody @Valid CreateUserRequestDto requestDto) {
    CreateUserResponseDto user = authUseCase.createUser(requestDto);

    return ResponseEntity
        .status(UserResponseCode.USER_CREATED.getStatus())
        .body(new ApiResponse<>(
            UserResponseCode.USER_CREATED.getMessage(),
            UserResponseCode.USER_CREATED.getStatus().value(),
            user
        ));
  }
}