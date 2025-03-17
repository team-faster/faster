package com.faster.user.app.user.infrastructure.persistence.jpa;

import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {

  private final UserJpaRepository userJpaRepository;
  private final UserRepositoryCustom userRepositoryCustom;

  @Override
  public Optional<User> findById(Long userId) {
    return userJpaRepository.findById(userId);
  }

  @Override
  public Optional<User> findUserByUsername(String username) {
    return userJpaRepository.findUserByUsername(username);
  }

  @Override
  public Optional<User> findUserByUsernameOrSlackId(String username, String slackId) {
    return userJpaRepository.findUserByUsernameOrSlackId(username, slackId);
  }

  @Override
  public User save(User user) {
    return userJpaRepository.save(user);
  }

  @Override
  public Page<QUserProjection> searchUsers(String username, String name, String slackId, Pageable pageable) {
    return userRepositoryCustom.searchUsers(username, name, slackId, pageable);
  }
}