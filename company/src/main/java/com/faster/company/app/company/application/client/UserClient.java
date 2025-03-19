package com.faster.company.app.company.application.client;

import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;

public interface UserClient {

  GetUserApplicationResponseDto getUserById(Long userId);
}
