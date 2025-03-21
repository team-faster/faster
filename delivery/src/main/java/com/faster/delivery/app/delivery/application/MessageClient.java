package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.deliverymanager.application.dto.SendMessageApplicationRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface MessageClient {
  void sendMessage(@RequestBody SendMessageApplicationRequestDto dto);
}
