package com.faster.user.app.user.application.usecase;

import static com.faster.user.app.global.exception.enums.AuthErrorCode.SIGN_IN_INVALID_PASSWORD;

import com.common.exception.CustomException;
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
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.service.UserDomainService;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserDomainService userDomainService;
  private final PasswordEncoder passwordEncoder;

  private User getUserByUserId(Long userId) {
    return userDomainService.findUserById(userId);
  }

  @Transactional
  @Override
  public AUpdateUserRoleResponseDto updateUserRoleByUserId(Long userId, AUpdateUserRoleRequestDto requestDto) {
    User user = userDomainService.findUserById(userId);
    user = userDomainService.updateUserRole(user, requestDto.role(), requestDto.updatedBy());

    return AUpdateUserRoleResponseDto.from(user);
  }

  @Override
  public PageResponse<AGetAllUserResponseDto> getAllUsers(String username,
                                                          String name,
                                                          String slackId,
                                                          Integer page,
                                                          Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<QUserProjection> pageResult = userDomainService.findUsers(username, name, slackId, pageable);

    return PageResponse.from(pageResult)
        .map(AGetAllUserResponseDto::from);
  }

  @Override
  public ADeleteUserResponseDto deleteUserByUserId(Long userId, ADeleteUserRequestDto requestDto) {
    User user = userDomainService.findUserById(userId);
    user.delete(LocalDateTime.now(), requestDto.deleterId());

    return ADeleteUserResponseDto.from(user.getId());
  }

  @Override
  public void updateUserPassword(Long userId, AUpdateUserPasswordRequestDto requestDto) {
    User user = userDomainService.findUserById(userId);

    if (!passwordEncoder.matches(requestDto.currentPassword(), user.getPassword())) {
      throw new CustomException(SIGN_IN_INVALID_PASSWORD);
    }

    String encodedPassword = passwordEncoder.encode(requestDto.newPassword());
    user.updateUserPassword(encodedPassword);

    userDomainService.save(user);
  }

  @Override
  public void updateUserSlackId(Long userId, AUpdateUserSlackIdRequestDto requestDto) {
    User user = getUserByUserId(userId);
    user.updateUserSlackId(requestDto.newSlackId());

    userDomainService.save(user);
  }

  @Override
  public AGetUserResponseDto getUserById(Long userId) {
    User user = getUserByUserId(userId);
    return AGetUserResponseDto.from(user);
  }

  @Override
  public AGetUserSlackIdResponseDto getUserSlackIdByUserId(Long userId) {
    User user = getUserByUserId(userId);
    return AGetUserSlackIdResponseDto.from(user);
  }

}
