package com.faster.message.app.message.application.usecase;


import com.faster.message.app.message.application.dto.request.ASaveMessageRequestDto;
import com.faster.message.app.message.application.dto.response.ASaveMessageResponseDto;

public interface MessageService {

  ASaveMessageResponseDto saveAndSendMessageByHubManager(ASaveMessageRequestDto requestDto);
}
