package com.faster.delivery.app.domains.delivery.infrastructure.jpa;

import com.faster.delivery.app.domains.delivery.domain.entity.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {

}
