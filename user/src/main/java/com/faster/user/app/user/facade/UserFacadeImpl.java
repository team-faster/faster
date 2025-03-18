package com.faster.user.app.user.facade;

import com.common.response.PageResponse;
import com.faster.user.app.user.application.dto.request.ADeleteUserRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserPasswordRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserRoleRequestDto;
import com.faster.user.app.user.application.dto.request.AUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.application.dto.response.AGetAllUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserResponseDto;
import com.faster.user.app.user.application.dto.response.AGetUserSlackIdResponseDto;
import com.faster.user.app.user.application.dto.response.AUpdateUserRoleResponseDto;
import com.faster.user.app.user.application.dto.response.ADeleteUserResponseDto;
import com.faster.user.app.user.application.usecase.UserService;
import com.faster.user.app.user.presentation.dto.request.PDeleteUserRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserPasswordRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserRoleRequestDto;
import com.faster.user.app.user.presentation.dto.request.PUpdateUserSlackIdRequestDto;
import com.faster.user.app.user.presentation.dto.response.PDeleteUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetAllUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserResponseDto;
import com.faster.user.app.user.presentation.dto.response.PGetUserSlackIdResponseDto;
import com.faster.user.app.user.presentation.dto.response.PUpdateUserRoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacadeImpl implements UserFacade {

  private final UserService userService;

  @Override
  public PUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, PUpdateUserRoleRequestDto requestDto) {
    // Presentation DTO → Application DTO
    AUpdateUserRoleRequestDto from = PUpdateUserRoleRequestDto.from(requestDto);
    // Service
    AUpdateUserRoleResponseDto responseDto = userService.updateUserRoleByUserId(userId, from);

    // Application DTO → Presentation DTO
    return PUpdateUserRoleResponseDto.from(responseDto);
  }

  @Override
  public PGetUserResponseDto getUserById(Long userId) {
    // Service → Application DTO
    AGetUserResponseDto responseDto = userService.getUserById(userId);

    // Application DTO → Presentation DTO
    return PGetUserResponseDto.from(responseDto);
  }

  @Override
  public PGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId) {
    // Service → Application DTO
    AGetUserSlackIdResponseDto responseDto = userService.getUserSlackIdByUserId(userId);

    // Application DTO → Presentation DTO
    return PGetUserSlackIdResponseDto.from(responseDto);
  }

  @Override
  public PageResponse<PGetAllUserResponseDto> getAllUsers(String username, String name, String slackId, Integer page,
                                                          Integer size) {
    // Service → application
    PageResponse<AGetAllUserResponseDto> appResponse = userService.getAllUsers(username, name, slackId, page, size);

    // application → Presentation
    return appResponse.map(PGetAllUserResponseDto::from);
  }

  @Override
  public PDeleteUserResponseDto deleteUserByUserId(Long userId, PDeleteUserRequestDto requestDto) {
    // Presentation → Application
    ADeleteUserRequestDto from = PDeleteUserRequestDto.from(requestDto);

    // Service
    ADeleteUserResponseDto responseDto = userService.deleteUserByUserId(userId, from);

    // Application → Presentation
    return PDeleteUserResponseDto.from(responseDto);
  }

  @Override
  public void updateUserPassword(Long userId, PUpdateUserPasswordRequestDto requestDto) {
    // Presentation → Application
    AUpdateUserPasswordRequestDto from = AUpdateUserPasswordRequestDto.from(requestDto);

    userService.updateUserPassword(userId, from);
  }

  @Override
  public void updateUserSlackId(Long userId, PUpdateUserSlackIdRequestDto requestDto) {
    // Presentation → Application
    AUpdateUserSlackIdRequestDto from = AUpdateUserSlackIdRequestDto.from(requestDto);

    userService.updateUserSlackId(userId, from);
  }
}
