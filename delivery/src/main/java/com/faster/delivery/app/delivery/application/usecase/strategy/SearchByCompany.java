package com.faster.delivery.app.delivery.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.delivery.application.CompanyClient;
import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import com.faster.delivery.app.delivery.domain.criteria.DeliveryCriteria;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchByCompany implements SearchByRole {
  private final DeliveryRepository deliveryRepository;
  private final CompanyClient companyClient;

  @Override
  public boolean isSupport(CurrentUserInfoDto userInfo) {
    return userInfo.role() == UserRole.ROLE_COMPANY;
  }

  @Override
  public Page<Delivery> getDeliveryList(Pageable pageable, Status status, CurrentUserInfoDto userInfo) {

    CompanyDto companyDto = companyClient.getCompanyByManagerId(userInfo.userId());
    DeliveryCriteria criteria = DeliveryCriteria.of(
        null, null,
        companyDto.companyId(), null, status);
    return deliveryRepository.searchDeliveryList(criteria, pageable);
  }
}
