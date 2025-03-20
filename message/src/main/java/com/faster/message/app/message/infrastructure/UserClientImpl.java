package com.faster.message.app.message.infrastructure;

import com.faster.message.app.message.application.client.UserClient;
import com.faster.message.app.message.application.dto.response.AGetUserResponseDto;
import com.faster.message.app.message.infrastructure.fegin.UserFeignClient;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

  private final UserFeignClient userFeignClient;

  @Override
  public AGetUserResponseDto getUserByUserId(Long userId) {

    IGetUserResponseDto data = userFeignClient.getUserById(userId).getBody().data();

    if (data == null) {
      return null; // TODO: 에러 처리 추후에 진행
    }

    return AGetUserResponseDto.builder()
        .name(data.name())
        .slackId(data.slackId())
        .build();
  }
}
