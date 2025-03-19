package com.faster.company.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CompanyErrorCode implements ErrorCode {
  INVALID_ID(
      HttpStatus.NOT_FOUND.value(),
      "업체 아이디가 유효하지 않습니다.",
      HttpStatus.NOT_FOUND),;

  private final int code;
  private final String message;
  private final HttpStatus status;
}
