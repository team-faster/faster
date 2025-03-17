package com.faster.gateway.app.global.security.service.dto;


import com.common.resolver.dto.UserRole;

public interface Authenticated {
  Long getId();
  String getUsername();
  String getPassword();
  UserRole getRole();
}
