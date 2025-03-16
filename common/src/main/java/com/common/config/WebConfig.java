package com.common.config;

import com.common.resolver.CurrentUserInfoResolver;
import com.common.resolver.CustomPageableArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final CurrentUserInfoResolver currentUserInfoResolver;
  private final CustomPageableArgumentResolver customPageableArgumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(currentUserInfoResolver);
    resolvers.add(customPageableArgumentResolver);
  }
}
