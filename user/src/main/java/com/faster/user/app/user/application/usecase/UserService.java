package com.faster.user.app.user.application.usecase;


import com.common.response.PageResponse;
import com.faster.user.app.user.application.dto.request.AUpdateUserRoleRequestDto;
import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserSlackIdResponseDto;
import com.faster.user.app.user.application.dto.response.AUpdateUserRoleResponseDto;

public interface UserService {

  AUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, AUpdateUserRoleRequestDto requestDto);

  PageResponse<AGetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                   Integer size);

  ADeleteUserResponseDto deleteUserByUserId(Long userId, ADeleteUserRequestDto requestDto);

  void updateUserPassword(Long userId, AUpdateUserPasswordRequestDto requestDto);

  void updateUserSlackId(Long userId, AUpdateUserSlackIdRequestDto requestDto);

  AGetUserResponseDto getUserById(Long userId);

  AGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId);
}