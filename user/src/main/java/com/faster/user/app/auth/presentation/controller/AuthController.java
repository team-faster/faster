package com.faster.user.app.auth.presentation.controller;

import com.common.response.ApiResponse;
import com.faster.user.app.auth.application.dto.SaveUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.application.usecase.AuthService;
import com.faster.user.app.auth.presentation.dto.requset.LogoutUserRequestDto;
import com.faster.user.app.auth.presentation.dto.response.SaveUserResponseDto;
import com.faster.user.app.auth.presentation.dto.response.SignInUserResponseDto;
import com.faster.user.app.global.response.enums.AuthResponseCode;
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

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<SaveUserResponseDto>> createUser(
      @RequestBody @Valid SaveUserRequestDto requestDto) {
    SaveUserResponseDto responseDto = authService.createUser(requestDto);

    return ResponseEntity
        .status(AuthResponseCode.USER_CREATED.getStatus())
        .body(new ApiResponse<>(
            AuthResponseCode.USER_CREATED.getMessage(),
            AuthResponseCode.USER_CREATED.getStatus().value(),
            responseDto
        ));
  }

  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<String>> signInUser(@RequestBody @Valid SignInUserRequestDto requestDto) {
    SignInUserResponseDto responseDto = authService.signInUser(requestDto);

    return ResponseEntity
        .status(AuthResponseCode.USER_SIGNED_IN.getStatus())
        .header("ACCESS-TOKEN", responseDto.accessToken())
        .header("REFRESH-TOKEN", responseDto.refreshToken())
        .body(new ApiResponse<>(
            AuthResponseCode.USER_SIGNED_IN.getMessage(),
            AuthResponseCode.USER_SIGNED_IN.getStatus().value(),
            null
        ));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutUserRequestDto requestDto) {
    authService.logout(requestDto.userId());

    return ResponseEntity
        .status(AuthResponseCode.USER_SUCCESS.getStatus())
        .body(new ApiResponse<>(
            AuthResponseCode.USER_SUCCESS.getMessage(),
            AuthResponseCode.USER_SUCCESS.getStatus().value(),
            null
        ));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<String>> generateNewAccessToken(@RequestBody LogoutUserRequestDto requestDto) {
    String generateNewAccessToken = authService.generateNewAccessToken(requestDto.userId());

    return ResponseEntity
        .status(AuthResponseCode.USER_SUCCESS.getStatus())
        .body(new ApiResponse<>(
            AuthResponseCode.USER_SUCCESS.getMessage(),
            AuthResponseCode.USER_SUCCESS.getStatus().value(),
            generateNewAccessToken
        ));
  }

}