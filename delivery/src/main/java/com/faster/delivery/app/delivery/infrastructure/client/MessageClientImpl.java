package com.faster.delivery.app.delivery.infrastructure.client;

import com.faster.delivery.app.delivery.application.MessageClient;
import com.faster.delivery.app.delivery.application.dto.SendMessageApplicationRequestDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.SendMessageFeignRequestDto;
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

/**
 * 아이디어 경로에 배송담당자 배송 가능시간 ~때부터는 배송이 가능하다
  */
