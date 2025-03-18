package com.faster.user.app.user.facade;

import com.common.response.PageResponse;
import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserPasswordRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.presentation.dto.response.PDeleteUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.response.PUpdateUserRoleResponseDto;

public interface UserFacade {
  PUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, PUpdateUserRoleRequestDto requestDto);
  PGetUserResponseDto getUserById(Long userId);
  PGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId);
  PageResponse<PGetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                   Integer size);

  PDeleteUserResponseDto deleteUserByUserId(Long userId, PDeleteUserRequestDto requestDto);

  void updateUserPassword(Long userId, PUpdateUserPasswordRequestDto requestDto);

  void updateUserSlackId(Long userId, PUpdateUserSlackIdRequestDto requestDto);
}