package com.faster.delivery.app.deliverymanager.infrastructure.redis;

import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import java.util.UUID;

public interface ManagerSequenceRepository {
  Long incrementManagerSequenceByCompanyId(UUID hubId, DeliveryManagerType type, int assignableManagerCount);
}
