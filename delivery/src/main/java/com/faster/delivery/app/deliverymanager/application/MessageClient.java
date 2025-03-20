package com.faster.delivery.app.deliverymanager.application;

import com.faster.delivery.app.deliverymanager.application.dto.SendMessageApplicationRequestDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.SendMessageFeignRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface MessageClient {
  void sendMessage(@RequestBody SendMessageApplicationRequestDto dto);
}
