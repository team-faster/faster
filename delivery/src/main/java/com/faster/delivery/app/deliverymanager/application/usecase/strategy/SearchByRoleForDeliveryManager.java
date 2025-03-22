package com.faster.delivery.app.deliverymanager.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchByRoleForDeliveryManager {
  boolean isSupport(CurrentUserInfoDto userInfo);
  Page<DeliveryManager> getDeliveryManagerList(Pageable pageable, String searchUserName, CurrentUserInfoDto userInfo);
}
