package com.faster.delivery.app.delivery.application.usecase.strategy;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchByRole {

  boolean isSupport(CurrentUserInfoDto userInfo);

  Page<Delivery> getDeliveryList(Pageable pageable, Status search, CurrentUserInfoDto userInfo);
}
