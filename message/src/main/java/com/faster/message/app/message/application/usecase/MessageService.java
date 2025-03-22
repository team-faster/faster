package com.faster.message.app.message.application.usecase;


import com.common.response.PageResponse;
import com.faster.message.app.message.application.dto.request.ASaveMessageRequestDto;
import com.faster.message.app.message.application.dto.response.ASaveMessageResponseDto;
import com.faster.message.app.message.presentation.dto.response.PGetAllMessageResponseDto;
import java.time.LocalDate;
import java.util.UUID;

public interface MessageService {

  ASaveMessageResponseDto saveAndSendMessageByHubManager(ASaveMessageRequestDto requestDto);

  PageResponse<PGetAllMessageResponseDto> getAllMessage(UUID targetSlackId, String content, String messageType, LocalDate sendAt, Integer page, Integer size);
}
