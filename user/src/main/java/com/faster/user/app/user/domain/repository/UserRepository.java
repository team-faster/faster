package com.faster.user.app.user.domain.repository;

import com.faster.user.app.user.domain.entity.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findUserByUsername(String username);
  Optional<User> findUserByUsernameOrSlackId(String username, String slackId);
}
