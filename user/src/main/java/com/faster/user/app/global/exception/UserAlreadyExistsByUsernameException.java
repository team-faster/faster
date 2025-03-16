package com.faster.user.app.global.exception;

public class UserAlreadyExistsByUsernameException extends RuntimeException {
  public UserAlreadyExistsByUsernameException(String message) {
    super(message);
  }
}
