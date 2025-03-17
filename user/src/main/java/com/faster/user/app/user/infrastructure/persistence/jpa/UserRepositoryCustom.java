package com.faster.user.app.user.infrastructure.persistence.jpa;

import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
  Page<QUserProjection> searchUsers(String username, String name, String slackId, Pageable pageable);
}
