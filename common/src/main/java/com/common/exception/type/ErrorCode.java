package com.common.exception.type;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  int getCode();

  String getMessage();

  HttpStatus getStatus();

  default int getHttpStatus() {
    return getStatus().value();
  }

}
