package com.faster.user.app.auth.application.usecase;


import com.faster.user.app.auth.application.dto.SaveUserRequestDto;
import com.faster.user.app.auth.application.dto.SignInUserRequestDto;
import com.faster.user.app.auth.presentation.dto.SaveUserResponseDto;
import com.faster.user.app.auth.presentation.dto.SignInUserResponseDto;

public interface AuthService {
  SaveUserResponseDto createUser(SaveUserRequestDto requestDto);
  SignInUserResponseDto signInUser(SignInUserRequestDto requestDto);
}
