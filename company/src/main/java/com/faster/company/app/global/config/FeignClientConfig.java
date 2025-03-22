package com.faster.company.app.global.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients("com.faster.company.app.company.infrastructure.feign")
public class FeignClientConfig {
  private static final String USER_ID_HEADER_NAME = "X-User-Id";
  private static final String USER_ROLE_HEADER_NAME = "X-User-Role";

  @Bean
  public RequestInterceptor requestInterceptor() {

    return requestTemplate -> {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();

      String userIdStr = request.getHeader(USER_ID_HEADER_NAME);
      String userRoleStr = request.getHeader(USER_ROLE_HEADER_NAME);
      if (userIdStr != null) {
        requestTemplate.header(USER_ID_HEADER_NAME, userIdStr);
      }
      if (userRoleStr != null) {
        requestTemplate.header(USER_ROLE_HEADER_NAME, userRoleStr);
      }
    };
  }
}