package com.faster.user.app.global.exception;

public class UserAlreadyExistsBySlackIdException extends RuntimeException {
  public UserAlreadyExistsBySlackIdException(String message) {
    super(message);
  }
}
