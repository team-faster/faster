package com.faster.user.app.user.infrastructure.persistence.jpa.dto;

import com.common.resolver.dto.UserRole;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class QUserProjection {
  private Long id;
  private String username;
  private String name;
  private String slackId;
  private UserRole role;
  private LocalDateTime createdAt;
  private Long createdBy;
  private LocalDateTime updatedAt;
  private Long updatedBy;
  private LocalDateTime deletedAt;
  private Long deletedBy;

  public QUserProjection(
      Long id,
      String username,
      String name,
      String slackId,
      UserRole role,
      LocalDateTime createdAt,
      Long createdBy,
      LocalDateTime updatedAt,
      Long updatedBy,
      LocalDateTime deletedAt,
      Long deletedBy
  ) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.slackId = slackId;
    this.role = role;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
    this.deletedAt = deletedAt;
    this.deletedBy = deletedBy;
  }
}