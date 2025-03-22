package com.faster.delivery.app.delivery.application.usecase.strategy;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.delivery.application.HubClient;
import com.faster.delivery.app.delivery.application.dto.HubDto;
import com.faster.delivery.app.delivery.domain.criteria.DeliveryCriteria;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchByHub implements SearchByRole {
  private final DeliveryRepository deliveryRepository;
  private final HubClient hubClient;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_HUB;
  }

  @Override
  public Page<Delivery> getDeliveryList(Pageable pageable, Status status, CurrentUserInfoDto userInfo) {

    HubDto hubDto = Optional.ofNullable(hubClient.getHubByHubManagerId(userInfo.userId()))
        .flatMap(hubs -> hubs.stream().findFirst())
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    DeliveryCriteria criteria = DeliveryCriteria.of(
        null, null, null,
        hubDto.hubId(), status);
    return deliveryRepository.searchDeliveryList(criteria, pageable);
  }
}
