package com.faster.delivery.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerErrorCode implements ErrorCode {
  NOT_FOUND(HttpStatus.NOT_FOUND.value(), "배송 담당자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  MISSING_HUB_ID(HttpStatus.BAD_REQUEST.value(), "허브 아이디가 필요합니다.", HttpStatus.BAD_REQUEST),
  MISSING_HUB(HttpStatus.NOT_FOUND.value(), "허브를 찾을 수 없습니다..", HttpStatus.NOT_FOUND),;


  private final int code;
  private final String message;
  private final HttpStatus status;
}
