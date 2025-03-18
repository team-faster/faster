package com.faster.hub.app.hub.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeleteHubApplicationRequestDto (
    UUID hubId,
    Long deleterId,
    LocalDateTime deletedAt
){
  public static DeleteHubApplicationRequestDto of(UUID hubId, Long deleterId, LocalDateTime deletedAt){
      return DeleteHubApplicationRequestDto.builder()
          .hubId(hubId)
          .deleterId(deleterId)
          .deletedAt(deletedAt)
          .build();
  }
}
