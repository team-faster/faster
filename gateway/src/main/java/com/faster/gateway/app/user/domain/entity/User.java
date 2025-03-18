package com.faster.gateway.app.user.domain.entity;

import com.common.domain.BaseEntity;
import com.common.resolver.dto.UserRole;
import com.faster.gateway.app.global.security.service.dto.Authenticated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements Authenticated {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "username", length = 100, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 500, nullable = false)
  private String password;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "slack_id", length = 200, nullable = false)
  private String slackId;

  @Enumerated(EnumType.STRING)
  private UserRole role;
}
