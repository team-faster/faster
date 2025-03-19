package com.faster.company.app.company.infrastructure;

import com.faster.company.app.company.application.client.UserClient;
import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;
import com.faster.company.app.company.infrastructure.feign.UserFeignClient;
import com.faster.company.app.company.infrastructure.feign.dto.response.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
  private final UserFeignClient userFeignClient;

  @Override
  public GetUserApplicationResponseDto getUserById(Long companyManagerId) {

    GetUserResponseDto userDto = userFeignClient.getUserById(companyManagerId).getBody().data();
    return userDto.toApplicationDto();
  }
}
