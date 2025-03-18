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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
@Entity
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @Column(name = "role", length = 255)
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

  // 회원 비밀번호 수정
  public void updateUserPassword(String password) {
    this.password = password;
  }

  // 회원 Slack ID 수정
  public void updateUserSlackId(String slackId) {
    this.slackId = slackId;
  }
}
