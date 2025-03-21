package com.faster.message.app.message.application.client;

import com.faster.message.app.message.application.dto.response.AGetUserResponseDto;

public interface UserClient {

  AGetUserResponseDto getUserByUserId(Long userId);

}