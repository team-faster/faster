package com.faster.user.app.user.infrastructure.persistence.jpa;

import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository, UserJpaRepositoryCustom   {

  Optional<User> findUserByUsername(String username);
  Optional<User> findUserByUsernameOrSlackId(String username, String slackId);
}
