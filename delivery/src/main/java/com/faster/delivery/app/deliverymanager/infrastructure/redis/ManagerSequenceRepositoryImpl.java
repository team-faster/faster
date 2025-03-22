package com.faster.delivery.app.deliverymanager.infrastructure.redis;

import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ManagerSequenceRepositoryImpl implements ManagerSequenceRepository{
  private final RedisTemplate<String, Object> redisTemplate;
  public static String prefix = "deliverymanager:hubid:%s:type:%s:sequance";

  @Override
  public Long incrementManagerSequenceByCompanyId(UUID hubId, DeliveryManagerType type, int assignableManagerCount){
    return redisTemplate
        .opsForValue()
        .increment(String.format(prefix, hubId, type.name()), assignableManagerCount);
  }
}
