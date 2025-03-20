package com.faster.delivery.app.global.common;

public class CommonUtil {
  // 빈문자열 or null 이면 true
  public static boolean checkStringIsEmpty (String target) {
    return target == null || target.isEmpty();
  }
}