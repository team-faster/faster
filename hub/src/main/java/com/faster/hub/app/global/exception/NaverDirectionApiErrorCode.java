package com.faster.hub.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NaverDirectionApiErrorCode implements ErrorCode {

  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "Naver api 서버가 일시적으로 사용 불가능한 상태입니다.", HttpStatus.SERVICE_UNAVAILABLE);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
