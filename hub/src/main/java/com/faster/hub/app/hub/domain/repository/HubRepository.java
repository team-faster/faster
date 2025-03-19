package com.faster.hub.app.hub.domain.repository;

import com.faster.hub.app.hub.domain.entity.Hub;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface HubRepository {
  Hub save(Hub hub);

  Optional<Hub> findById(UUID hubId);

  @EntityGraph(attributePaths = {"routesFromSource"})
  List<Hub> findAll();
}
