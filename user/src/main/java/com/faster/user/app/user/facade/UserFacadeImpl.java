package com.faster.user.app.user.facade;

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
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacadeImpl implements UserFacade {

  private final UserService userService;

  @Override
  public AUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, AUpdateUserRoleRequestDto requestDto) {
    return userService.updateUserRoleByUserId(userId, requestDto);
  }

  @Override
  public AGetUserResponseDto getUserById(Long userId) {
    return userService.getUserById(userId);
  }

  @Override
  public AGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId) {
    return userService.getUserSlackIdByUserId(userId);
  }

  @Override
  public PageResponse<AGetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                          Integer size) {
    return userService.getAllUsers(username, name, slackId, page, size);
  }


  @Override
  public ADeleteUserResponseDto deleteUserByUserId(Long userId, ADeleteUserRequestDto requestDto) {
    return userService.deleteUserByUserId(userId, requestDto);
  }

  @Override
  public void updateUserPassword(Long userId, AUpdateUserPasswordRequestDto requestDto) {
    userService.updateUserPassword(userId, requestDto);
  }

  @Override
  public void updateUserSlackId(Long userId, AUpdateUserSlackIdRequestDto requestDto) {
    userService.updateUserSlackId(userId, requestDto);
  }
}
