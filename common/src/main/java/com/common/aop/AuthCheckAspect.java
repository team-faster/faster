package com.common.aop;

import com.common.aop.annotation.AuthCheck;
import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic="AUTH CHECK::")
public class AuthCheckAspect {

  @Before("@annotation(authCheck)")
  public void authCheck(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable {

    Set<UserRole> roles = Set.of(authCheck.roles());
    CurrentUserInfoDto userInfoDto = getCurrentUserInfoDto(joinPoint.getArgs());
    log.debug("유저 아이디: {}, 유저 권한: {} - 필요 권한: {}", userInfoDto.userId(), userInfoDto.role(), roles);

    if (!roles.contains(userInfoDto.role())) {
      throw CustomException.from(ApiErrorCode.FORBIDDEN);
    }
  }

  private CurrentUserInfoDto getCurrentUserInfoDto(Object[] args) {
    return Arrays.stream(args)
        .filter(arg -> arg instanceof CurrentUserInfoDto && arg != null)
        .map(CurrentUserInfoDto.class::cast)
        .findFirst()
        .orElseThrow(() -> CustomException.from(ApiErrorCode.UNAUTHORIZED));
  }
}
