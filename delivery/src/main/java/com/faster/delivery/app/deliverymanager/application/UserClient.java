package com.faster.delivery.app.deliverymanager.application;

import com.faster.delivery.app.deliverymanager.application.dto.UserDto;

public interface UserClient {
  UserDto getUserData(Long userId);
}
