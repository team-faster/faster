package com.faster.product.app.global.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
  INVALID_ID(
      HttpStatus.NOT_FOUND.value(),
      "존재하지 않는 상품 아이디입니다.",
      HttpStatus.NOT_FOUND);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
