package com.common.aop;

import com.common.aop.annotation.AuthCheck;
import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j(topic="AUTH CHECK::")
public class AuthCheckAspect {

  @Before("@annotation(authCheck)")
  public void authCheck(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable {

    Set<UserRole> roles = Set.of(authCheck.roles());
    UserRole currentUserRole = getCurrentUserRole();
    log.debug("유저 권한: {} - 필요 권한: {}", currentUserRole, roles);

    if (!roles.contains(currentUserRole)) {
      throw CustomException.from(ApiErrorCode.FORBIDDEN);
    }
  }

  private UserRole getCurrentUserRole() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
      throw CustomException.from(ApiErrorCode.UNAUTHORIZED);
    }
    HttpServletRequest request = attributes.getRequest();
    String userRoleStr = request.getHeader("X-User-Role");
    return UserRole.valueOf(userRoleStr);
  }
}
