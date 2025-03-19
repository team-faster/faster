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

  @Bean
  public RequestInterceptor requestInterceptor() {

    return requestTemplate -> {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();

      String userIdStr = request.getHeader("X-User-Id");
      String userRoleStr = request.getHeader("X-User-Role");
      if (userIdStr != null) {
        requestTemplate.header("X-User-Id", userIdStr);
      }
      if (userRoleStr != null) {
        requestTemplate.header("X-User-Role", userRoleStr);
      }
    };
  }
}