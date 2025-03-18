package com.faster.user.app.user.application.usecase;


import com.common.response.PageResponse;
import com.faster.user.app.user.presentation.dto.DeleteUserResponseDto;
import com.faster.user.app.user.application.dto.DeleteUserRequestDto;
import com.faster.user.app.user.application.dto.UpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.GetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.UpdateUserRoleResponseDto;

public interface UserService {

  UpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, UpdateUserRoleRequestDto requestDto);

  PageResponse<GetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                  Integer size);

  DeleteUserResponseDto deleteUserByUserId(Long userId, DeleteUserRequestDto requestDto);
}