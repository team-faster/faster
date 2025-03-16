package com.faster.user.app.auth.application.usecase;


import com.faster.user.app.auth.application.dto.CreateUserRequestDto;
import com.faster.user.app.user.presentation.dto.CreateUserResponseDto;

public interface AuthUseCase {
  CreateUserResponseDto createUser(CreateUserRequestDto requestDto);
}
