package com.faster.user.app.user.domain.entity;

import com.common.domain.BaseEntity;
import com.common.resolver.dto.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
@Entity
public class User extends BaseEntity {

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

  private User(String username, String password, String name, String slackId) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.slackId = slackId;
  }

  public static User of(String username, String password, String name, String slackId) {
    return new User(username, password, name, slackId);
  }

  // 회원 권한 수정
  public void updateUserRole(UserRole role) {
    this.role = role;
  }
}
