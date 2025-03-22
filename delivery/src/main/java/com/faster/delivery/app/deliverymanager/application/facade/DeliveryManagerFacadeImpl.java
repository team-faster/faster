package com.faster.delivery.app.deliverymanager.application.facade;

import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.usecase.DeliveryManagerService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryManagerFacadeImpl implements DeliveryManagerFacade {
  private final DeliveryManagerService deliveryManagerService;
  private final RedissonClient redissonClient;
  private static final String DELIVERY_MANAGER_SEQUENCE_PREFIX = "deliverymanager:sequence:hubid:";

  @Override
  public Long saveDeliveryManager(DeliveryManagerSaveDto saveDto) {
    RLock lock = redissonClient.getLock(DELIVERY_MANAGER_SEQUENCE_PREFIX + saveDto.hubId());

    try {
      // 몇 초 동안 락 획득을 시도할 것인지, 몇 초 동안 점유할 것인지를 설정
      boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

      if (!available) {
        log.info("lock 획득 실패");
        return null;
      }

      return deliveryManagerService.saveDeliveryManager(saveDto);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }
}
