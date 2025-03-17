package com.faster.user.app.user.domain.repository;

import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
  Optional<User> findById(Long userId);

  Optional<User> findUserByUsername(String username);

  Optional<User> findUserByUsernameOrSlackId(String username, String slackId);

  User save(User user);

  Page<QUserProjection> searchUsers(String username, String name, String slackId, Pageable pageable);

}