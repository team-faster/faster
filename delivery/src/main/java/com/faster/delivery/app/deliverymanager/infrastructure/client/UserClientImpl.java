package com.faster.delivery.app.deliverymanager.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.UserClient;
import com.faster.delivery.app.deliverymanager.application.dto.UserDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.UserGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements UserClient {

  private final UserFeginClient userFeginClient;

  @Override
  public UserDto getUserData(Long userId) {
    ApiResponse<UserGetResponseDto> userData = userFeginClient.getUserData(userId);
    UserGetResponseDto data = userData.data();
    return UserDto.from(data);
  }


}
