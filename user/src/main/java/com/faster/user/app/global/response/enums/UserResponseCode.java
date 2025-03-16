package com.faster.user.app.global.response.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode {

  USER_SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
  USER_CREATED(HttpStatus.CREATED,  "회원가입이 완료되었습니다."),
  USER_UPDATED(HttpStatus.OK,  "회원 정보가 수정되었습니다."),
  USER_DELETED(HttpStatus.OK,  "회원이 삭제되었습니다."),
  USER_FETCHED(HttpStatus.OK, "회원 정보를 성공적으로 조회했습니다."),
  USERS_FETCHED(HttpStatus.OK, "전체 회원 목록을 성공적으로 조회했습니다.");

  private final HttpStatus status;
  private final String message;
}