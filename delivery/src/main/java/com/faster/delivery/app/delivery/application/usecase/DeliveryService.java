package com.faster.delivery.app.delivery.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.PageResponse;
import com.faster.delivery.app.delivery.application.dto.DeliveryDetailDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryGetElementDto;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {
  UUID saveDelivery(DeliverySaveDto deliverySaveDto);
  DeliveryDetailDto getDeliveryDetail(UUID deliveryId, CurrentUserInfoDto userInfoDto);
  UUID updateDeliveryStatus (
      CurrentUserInfoDto userInfoDto, UUID deliveryId, DeliveryUpdateDto deliveryUpdateDto);
  UUID deleteDelivery(UUID deliveryId, CurrentUserInfoDto userInfoDto);

  UUID saveDeliveryInternal(DeliverySaveDto deliverySaveDto);
  UUID updateDeliveryStatusInternal(
      UUID deliveryId, DeliveryUpdateDto deliveryUpdateDto, CurrentUserInfoDto userInfoDto);

  PageResponse<DeliveryGetElementDto> getDeliveryList(
      Pageable pageable, String search, CurrentUserInfoDto userInfoDto);
}
