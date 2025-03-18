package com.faster.gateway.app.global.filter;

import com.faster.gateway.app.global.security.service.dto.CustomUserDetails;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserInfoFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();

    return context
        .flatMap(securityContext -> {
          Authentication authentication = securityContext.getAuthentication();
          Object principal = authentication.getPrincipal();
          Long userId = null;
          String userRole = null;

          if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            userId = userDetails.getMemberId();
            userRole = userDetails.getAuthorities().iterator().next().getAuthority();
          }

          if (userId != null) {
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Role", userRole)
                .build();

            // 새로운 요청을 포함하는 exchange를 생성
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

            return chain.filter(modifiedExchange);
          }

          return chain.filter(exchange);
        })
        .switchIfEmpty(chain.filter(exchange));
  }
}

