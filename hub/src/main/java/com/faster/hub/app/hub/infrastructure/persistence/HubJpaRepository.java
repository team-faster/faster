package com.faster.hub.app.hub.infrastructure.persistence;

import com.faster.hub.app.hub.domain.entity.Hub;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends HubRepository, JpaRepository<Hub, UUID> {

}
