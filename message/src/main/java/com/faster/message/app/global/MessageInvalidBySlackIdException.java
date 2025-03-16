package com.faster.message.app.global;

public class MessageInvalidBySlackIdException extends RuntimeException {
  public MessageInvalidBySlackIdException(String message) {
    super(message);
  }
}
