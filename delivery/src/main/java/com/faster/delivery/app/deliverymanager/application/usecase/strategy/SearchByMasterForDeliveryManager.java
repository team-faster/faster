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
public class SearchByMasterForDeliveryManager implements SearchByRoleForDeliveryManager {
  private final DeliveryManagerRepository deliveryManagerRepository;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_MASTER;
  }

  @Override
  public Page<DeliveryManager> getDeliveryManagerList(Pageable pageable, String searchUserName, CurrentUserInfoDto userInfo) {
    DeliveryManagerCriteria criteria =
        DeliveryManagerCriteria.of(null, null, searchUserName);
    return deliveryManagerRepository.searchDeliveryManagerList(criteria, pageable);
  }
}
