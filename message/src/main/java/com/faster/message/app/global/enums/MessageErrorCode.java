package com.faster.message.app.global.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessageErrorCode implements ErrorCode {

  // Message (메시지 관련 오류)
  MESSAGE_INVALID_SEND_AT(HttpStatus.BAD_REQUEST.value(), "현재 시간보다 이전으로 메시지를 보낼 수 없습니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_INVALID_SLACK_ID(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 Slack ID 입니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_TYPE_NULL(HttpStatus.BAD_REQUEST.value(), "메시지 타입이 비어 있습니다.", HttpStatus.BAD_REQUEST),
  MESSAGE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "메시지 타입이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

  // Slack (슬랙 관련 오류)
  SLACK_OPEN_DM_FAILED(HttpStatus.BAD_REQUEST.value(), "DM 채널을 열 수 없습니다.", HttpStatus.BAD_REQUEST),
  SLACK_CREATE_DM_FAILED(HttpStatus.BAD_REQUEST.value(), "DM 채널을 생성할 수 없습니다.", HttpStatus.BAD_REQUEST),
  SLACK_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "워크스페이스에서 해당 이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  SLACK_ERROR_BY_EMAIL_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이메일 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


  private final int code;
  private final String message;
  private final HttpStatus status;
}
