package com.faster.gateway.app.global.security.service;

import com.faster.gateway.app.global.security.service.dto.CustomUserDetails;
import com.faster.gateway.app.global.security.service.dto.UserDetailsDto;
import com.faster.gateway.app.global.exception.GatewayErrorCode;
import com.faster.gateway.app.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return Mono.fromSupplier(() -> new CustomUserDetails(getUserDetailsDomain(username)));
  }

  private UserDetailsDto getUserDetailsDomain(String username) {
    return UserDetailsDto.from(userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            GatewayErrorCode.USER_NOT_FOUND.getMessage()
        )));
  }
}