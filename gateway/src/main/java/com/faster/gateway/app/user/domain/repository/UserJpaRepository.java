package com.faster.gateway.app.user.domain.repository;

import com.faster.gateway.app.user.domain.entity.User;
import com.faster.gateway.app.user.infrastructure.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {

}
