package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.PageResponse;
import com.faster.delivery.app.deliverymanager.application.dto.AssignCompanyDeliveryManagerApplicationResponse;
import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationResponse;
import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationRequestDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerElementDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface DeliveryManagerService {
  Long saveDeliveryManager(DeliveryManagerSaveDto saveDto);
  DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, Long deliveryManagerId);
  PageResponse<DeliveryManagerElementDto> getDeliveryManagerList(
      Pageable pageable, String search, CurrentUserInfoDto userInfo);
  Long updateDeliveryManager(Long deliveryManagerId, DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo);
  Long deleteDeliveryManager(Long deliveryManagerId, CurrentUserInfoDto userInfo);

  DeliveryManagerDetailDto getDeliveryManagerDetailInternal(CurrentUserInfoDto userInfo, Long deliveryManagerId);

  DeliveryManagerDetailDto getDeliveryManagerByUserIdInternal(CurrentUserInfoDto userInfo, Long userId);

  AssignDeliveryManagerApplicationResponse assignDeliveryManger(
      AssignDeliveryManagerApplicationRequestDto dto);
}