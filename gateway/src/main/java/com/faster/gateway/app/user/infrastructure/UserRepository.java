package com.faster.gateway.app.user.infrastructure;

import com.faster.gateway.app.user.domain.entity.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByUsername(String username);

  Optional<User> findById(Long userId);

}
