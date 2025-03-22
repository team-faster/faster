package com.faster.delivery.app.deliverymanager.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.deliverymanager.domain.criteria.DeliveryManagerCriteria;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchByDeliveryForDeliveryManager implements SearchByRoleForDeliveryManager {
  private final DeliveryManagerRepository deliveryManagerRepository;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_DELIVERY;
  }

  @Override // 본인 것만 조회
  public Page<DeliveryManager> getDeliveryManagerList(Pageable pageable, String searchUserName,
      CurrentUserInfoDto userInfo) {

    // 허브 배송 담당자
    DeliveryManagerCriteria criteria = DeliveryManagerCriteria.of(userInfo.userId(), null, searchUserName);
    return deliveryManagerRepository.searchDeliveryManagerList(criteria, pageable);
  }
}