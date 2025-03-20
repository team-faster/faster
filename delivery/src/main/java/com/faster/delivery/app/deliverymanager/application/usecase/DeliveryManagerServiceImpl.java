package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.deliverymanager.application.HubClient;
import com.faster.delivery.app.deliverymanager.application.UserClient;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.application.dto.UserDto;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager.Type;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

  private final HubClient hubClient;
  private final UserClient userClient;
  private final DeliveryManagerRepository deliveryManagerRepository;

  @Transactional
  public UUID saveDeliveryManager(DeliveryManagerSaveDto saveDto) {
    // 허브 조건 조회
    List<HubDto> hubListData = hubClient.getHubListData(List.of(saveDto.hubId()));
    HubDto hubData = hubListData.get(0);

    // 유저 정보 조회
    UserDto userData = userClient.getUserData(saveDto.userId());

    // 배송 기사 정보 구성 및 save
    DeliveryManager deliveryManager = DeliveryManager.builder()
        .userId(userData.userId())
        .userName(userData.name())
        .hubId(hubData.hubId())
        .type(getDeliveryManagerTypeByString(saveDto.type()))
        .deliverySequenceNumber(1) // TODO : 동시성 고려 처리
        .build();

    DeliveryManager savedDeliveryManager = deliveryManagerRepository.save(deliveryManager);
    return savedDeliveryManager.getId();
  }

  public DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, UUID deliveryManagerId) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfo, deliveryManagerId, deliveryManager);

    // dto 변환
    DeliveryManagerDetailDto deliveryManagerDetailDto = DeliveryManagerDetailDto.from(deliveryManager);
    return deliveryManagerDetailDto;
  }

  @Transactional
  public UUID updateDeliveryManager(UUID deliveryManagerId,
      DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfo, deliveryManagerId, deliveryManager);

    Type newType = getDeliveryManagerTypeByString(updateDto.type());

    // update
    deliveryManager.update(newType, updateDto.deliverySequenceNumber());
    return deliveryManager.getId();
  }

  @Transactional
  public UUID deleteDeliveryManager(UUID deliveryManagerId, CurrentUserInfoDto userInfo) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfo, deliveryManagerId, deliveryManager);

    // delete
    deliveryManager.delete(LocalDateTime.now(), userInfo.userId());
    return deliveryManager.getId();
  }

  public DeliveryManagerDetailDto getDeliveryManagerDetailInternal(CurrentUserInfoDto userInfo, UUID deliveryManagerId) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfo, deliveryManagerId, deliveryManager);

    // dto 변환
    DeliveryManagerDetailDto deliveryManagerDetailDto = DeliveryManagerDetailDto.from(deliveryManager);
    return deliveryManagerDetailDto;
  }

  private void checkRole(
      CurrentUserInfoDto userInfo, UUID deliveryManagerId, DeliveryManager deliveryManager) {

    switch (userInfo.role()) {

      case ROLE_DELIVERY -> {
        if (!userInfo.userId().equals(deliveryManagerId)) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }

      case ROLE_HUB -> {
        List<HubDto> hubListData = hubClient.getHubListData(List.of(deliveryManager.getHubId()));
        if (CollectionUtils.isEmpty(hubListData)) {
          throw new CustomException(ApiErrorCode.INVALID_REQUEST);
        }
        HubDto hubData = hubListData.get(0);
        if (hubData.hubManagerId().equals(userInfo.userId())) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
    }
  }

  private static Type getDeliveryManagerTypeByString(String typeString) {
    try {
      return Type.valueOf(typeString);
    } catch (Exception e) {
      throw new CustomException(ApiErrorCode.INVALID_REQUEST);
    }
  }
}
