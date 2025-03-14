package com.common.exception;

import com.common.exception.type.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public static CustomException from(ErrorCode errorCode) {
    return new CustomException(errorCode);
  }

  public int getCode() {
    return errorCode.getCode();
  }

  public int getHttpStatus() {
    return errorCode.getHttpStatus();
  }

}
