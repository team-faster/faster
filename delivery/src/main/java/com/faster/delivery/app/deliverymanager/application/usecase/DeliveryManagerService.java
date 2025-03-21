package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.deliverymanager.application.dto.AssignCompanyDeliveryManagerApplicationResponse;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import java.util.UUID;

public interface DeliveryManagerService {
  Long saveDeliveryManager(DeliveryManagerSaveDto saveDto);
  DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, Long deliveryManagerId);
  Long updateDeliveryManager(Long deliveryManagerId, DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo);
  Long deleteDeliveryManager(Long deliveryManagerId, CurrentUserInfoDto userInfo);

  DeliveryManagerDetailDto getDeliveryManagerDetailInternal(CurrentUserInfoDto userInfo, Long deliveryManagerId);

  DeliveryManagerDetailDto getDeliveryManagerByUserIdInternal(CurrentUserInfoDto userInfo, Long userId);

  AssignCompanyDeliveryManagerApplicationResponse assignCompanyDeliveryManger(UUID hubId);
}