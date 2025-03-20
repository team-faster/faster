package com.faster.hub.app.hub.domain.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public record SearchHubProjection (
    UUID id,
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){

}
