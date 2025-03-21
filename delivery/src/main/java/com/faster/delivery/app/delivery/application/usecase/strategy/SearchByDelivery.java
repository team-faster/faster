package com.faster.delivery.app.delivery.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.domain.criteria.DeliveryCriteria;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchByDelivery implements SearchByRole {
  private final DeliveryRepository deliveryRepository;
  private final DeliveryManagerClient deliveryManagerClient;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_DELIVERY;
  }

  @Override
  public Page<Delivery> getDeliveryList(Pageable pageable, Status status,
      CurrentUserInfoDto userInfo) {

    DeliveryManagerDto deliveryManagerDto =
        deliveryManagerClient.getDeliveryManagerByUserId(userInfo.userId());

    // 허브 배송 담당자
    if (deliveryManagerDto.type().equals("HUB_DELIVERY")) {
      DeliveryCriteria criteria = DeliveryCriteria.of(
          deliveryManagerDto.deliveryManagerId(),
          null, null, null, status);
      return deliveryRepository.searchDeliveryList(criteria, pageable);
    }
    // 업체 배송 담당자
    if (deliveryManagerDto.type().equals("COMPANY_DELIVERY")) {
      DeliveryCriteria criteria = DeliveryCriteria.of(
          null, deliveryManagerDto.deliveryManagerId(),
          null, null, status);
      return deliveryRepository.searchDeliveryList(criteria, pageable);
    }
    return null;
  }
}
