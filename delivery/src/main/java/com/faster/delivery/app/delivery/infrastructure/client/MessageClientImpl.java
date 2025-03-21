package com.faster.delivery.app.delivery.infrastructure.client;

import com.faster.delivery.app.delivery.application.MessageClient;
import com.faster.delivery.app.deliverymanager.application.dto.SendMessageApplicationRequestDto;
import com.faster.delivery.app.deliverymanager.infrastructure.client.dto.SendMessageFeignRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageClientImpl implements MessageClient {
  private final MessageFeignClient messageFeignClient;


  @Override
  public void sendMessage(SendMessageApplicationRequestDto dto) {
    messageFeignClient.sendMessage(
        SendMessageFeignRequestDto.from(dto));
  }
}
