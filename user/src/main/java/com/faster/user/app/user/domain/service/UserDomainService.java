package com.faster.user.app.user.domain.service;

import com.common.exception.CustomException;
import com.common.resolver.dto.UserRole;
import com.faster.user.app.global.exception.enums.UserErrorCode;
import com.faster.user.app.user.domain.entity.User;
import com.faster.user.app.user.domain.repository.UserRepository;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDomainService {

  private final UserRepository userRepository;

  public User save(User user) {
    return userRepository.save(user);
  }

  public User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  public User updateUserRole(User user, UserRole newRole, Long updatedBy) {
    user.updateUserRole(newRole);
    user.createdBy(updatedBy);
    return userRepository.save(user);
  }

  public Page<QUserProjection> findUsers(String username, String name, String slackId, Pageable pageable) {
    return userRepository.searchUsers(username, name, slackId, pageable);
  }

}