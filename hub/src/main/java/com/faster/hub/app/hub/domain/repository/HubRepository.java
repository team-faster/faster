package com.faster.hub.app.hub.domain.repository;

import com.faster.hub.app.hub.domain.entity.Hub;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {
  Hub save(Hub hub);

  Optional<Hub> findById(UUID hubId);
}
