package com.faster.delivery.app.deliverymanager.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.delivery.application.HubClient;
import com.faster.delivery.app.delivery.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.domain.criteria.DeliveryManagerCriteria;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchByHubForDeliveryManager implements SearchByRoleForDeliveryManager {
  private final DeliveryManagerRepository deliveryManagerRepository;
  private final HubClient hubClient;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_HUB;
  }

  @Override
  public Page<DeliveryManager> getDeliveryManagerList(Pageable pageable, String searchUserName, CurrentUserInfoDto userInfo) {

    List<HubDto> hubByHubManagerId = hubClient.getHubByHubManagerId(userInfo.userId());
    List<UUID> hubIdList = hubByHubManagerId.stream().map(HubDto::hubId).toList();

    DeliveryManagerCriteria criteria = DeliveryManagerCriteria.of(
        null, hubIdList, searchUserName);
    return deliveryManagerRepository.searchDeliveryManagerList(criteria, pageable);
  }
}
