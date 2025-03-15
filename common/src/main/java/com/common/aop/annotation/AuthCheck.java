package com.common.aop.annotation;

import com.common.resolver.dto.UserRole;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {
  UserRole[] roles()
      default {UserRole.ROLE_MASTER, UserRole.ROLE_HUB, UserRole.ROLE_DELIVERY, UserRole.ROLE_COMPANY};
}