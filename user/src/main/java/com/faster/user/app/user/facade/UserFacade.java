package com.faster.user.app.user.facade;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.PageResponse;
import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserRoleRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserSlackIdResponseDto;
import com.faster.user.app.user.application.dto.response.AUpdateUserRoleResponseDto;
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
  AUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, AUpdateUserRoleRequestDto requestDto);
  AGetUserResponseDto getUserById(Long userId);
  AGetUserResponseDto getMyUserById(Long userId, CurrentUserInfoDto userInfo);
  AGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId);
  PageResponse<AGetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                   Integer size);

  ADeleteUserResponseDto deleteUserByUserId(Long userId, ADeleteUserRequestDto requestDto);
  void updateUserPassword(Long userId, AUpdateUserPasswordRequestDto requestDto);

  void updateUserSlackId(Long userId, AUpdateUserSlackIdRequestDto requestDto);
}