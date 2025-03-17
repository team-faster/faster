package com.faster.user.app.user.infrastructure.persistence.jpa;

import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserQuerydslResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserJpaRepositoryCustom {
  Page<QUserQuerydslResponseDto> searchUsers(String username, String name, String slackId, Pageable pageable);
}
