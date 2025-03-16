package com.faster.user.app.user.domain.repository;

import com.faster.user.app.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByUsername(String username);
  Optional<User> findUserByUsernameOrSlackId(String username, String slackId);
}
