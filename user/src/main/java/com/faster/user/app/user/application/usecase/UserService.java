package com.faster.user.app.user.application.usecase;


import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;

public interface UserService {

  UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto);
}
