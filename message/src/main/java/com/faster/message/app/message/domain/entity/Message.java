package com.faster.message.app.message.domain.entity;

import com.common.domain.BaseEntity;
import com.faster.message.app.message.domain.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Table(name = "p_message")
@Entity
public class Message extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "target_slack_id", nullable = false, length = 200)
  private String targetSlackId;

  @Column(name = "contents", columnDefinition = "TEXT")
  private String contents;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private Type type;

  @Column(name = "send_at", nullable = false)
  private LocalDateTime sendAt;

  private Message(String targetSlackId, String contents, Type type, LocalDateTime sendAt) {
    this.targetSlackId = targetSlackId;
    this.contents = contents;
    this.type = type;
    this.sendAt = sendAt;
  }

  public static Message of(String targetSlackId, String contents, Type type, LocalDateTime sendAt) {
    return new Message(targetSlackId, contents, type, sendAt);
  }
}
