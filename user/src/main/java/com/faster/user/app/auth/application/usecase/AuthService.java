package com.faster.user.app.auth.application.usecase;


import com.faster.user.app.auth.application.dto.CreateUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.presentation.dto.CreateUserResponseDto;
import com.faster.user.app.auth.presentation.dto.SignInUserResponseDto;

public interface AuthService {
  CreateUserResponseDto createUser(CreateUserRequestDto requestDto);
  SignInUserResponseDto signInUser(SignInUserRequestDto requestDto);
}
