package com.faster.delivery.app.deliverymanager.infrastructure.redis;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyManagerSequenceRepository {
  private final RedisTemplate<String, Object> redisTemplate;
  public static String prefix = "company_manager_sequence _number:hub_id:%s:type:%s";

  public Long incrementCompanyManagerSequenceByCompanyId(UUID hubId, String type){
    return redisTemplate
        .opsForValue()
        .increment(String.format(prefix, hubId, type));
  }
}
