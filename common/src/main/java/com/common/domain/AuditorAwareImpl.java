package com.common.domain;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String userId = request.getHeader("X-User-Id");

      if (userId != null) {
        try {
          return Optional.of(Long.valueOf(userId));
        } catch (NumberFormatException e) {
          return Optional.empty();
        }
      }
    }
    return Optional.empty();
  }
}
