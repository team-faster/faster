package com.faster.user.app.global.response.enums;

import com.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode implements ErrorCode {

  USER_UPDATED(HttpStatus.OK.value(), "회원 정보를 성공적으로 수정했습니다.", HttpStatus.OK);

  private final int code;
  private final String message;
  private final HttpStatus status;
}
