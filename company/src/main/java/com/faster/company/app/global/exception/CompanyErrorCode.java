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
      HttpStatus.NOT_FOUND),
  INVALID_SAVE_REQ(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "업체 생성 요청이 부적합합니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INVALID_MANAGER(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "업체 매니저 아이디가 유효하지 않습니다.",
      HttpStatus.UNPROCESSABLE_ENTITY),
  FORBIDDEN(
      HttpStatus.FORBIDDEN.value(),
      "해당 업체에 대한 접근 권한이 없습니다.",
      HttpStatus.FORBIDDEN),;


  private final int code;
  private final String message;
  private final HttpStatus status;
}
