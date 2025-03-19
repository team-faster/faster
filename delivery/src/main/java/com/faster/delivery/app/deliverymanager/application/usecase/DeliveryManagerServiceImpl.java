package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

  private final HubClient hubClient;
  private final UserClient userClient;
  private final DeliveryManagerRepository deliveryManagerRepository;

  @Transactional
  public UUID saveDeliveryManager(DeliveryManagerSaveDto saveDto) {
    log.info("save delivery manager : {}", saveDto);
    // 허브 조건 조회
    // HubDto hubData = hubClient.getHubData(saveDto.hubId());
    HubDto testHub = HubDto.builder()
        .hubId(UUID.randomUUID())
        .name("테스트허브이름")
        .address("테스트 허브 주소")
        .latitude("123.123")
        .longitude("1234.1234")
        .build();

    // 유저 정보 조회
    // UserDto userData = userClient.getUserData(saveDto.userId());
    UserDto testUser = UserDto.builder()
        .userId(1L)
        .build();

    // 배송 기사 정보 구성 및 save
//    DeliveryManager deliveryManager = DeliveryManager.builder()
//        .userId(userData.userId())
//        .userName(userData.name())
//        .hubId(hubData.hubId())
//        .type(getDeliveryManagerTypeByString(saveDto))
//        .deliverySequenceNumber(1) // TODO : 동시성 고려 처리
//        .build();

    DeliveryManager deliveryManager = DeliveryManager.builder()
        .userId(testUser.userId())
        .userName(testUser.name())
        .hubId(testHub.hubId())
        .type(getDeliveryManagerTypeByString(saveDto.type()))
        .deliverySequenceNumber(1)
        .build();

    DeliveryManager savedDeliveryManager = deliveryManagerRepository.save(deliveryManager);
    return savedDeliveryManager.getId();
  }

  public DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, UUID deliveryManagerId) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // TODO : 권한 체크
    switch (userInfo.role()) {
      case ROLE_DELIVERY -> {

      }
      case ROLE_HUB -> {

      }
    }

    // TODO : dto 변환
    DeliveryManagerDetailDto deliveryManagerDetailDto = DeliveryManagerDetailDto.from(deliveryManager);
    return deliveryManagerDetailDto;
  }

  @Transactional
  public UUID updateDeliveryManager(UUID deliveryManagerId,
      DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    if (UserRole.ROLE_HUB.equals(userInfo.role())) {
      // TODO : 권한 체크
      // hub 담당자 조회
    }

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

    if (UserRole.ROLE_HUB.equals(userInfo.role())) {
      // TODO : 권한 체크
      // hub 담당자 조회
    }

    // delete
    deliveryManager.delete(LocalDateTime.now(), userInfo.userId());
    return deliveryManager.getId();
  }


  private static Type getDeliveryManagerTypeByString(String typeString) {
    try {
      return Type.valueOf(typeString);
    } catch (Exception e) {
      throw new CustomException(ApiErrorCode.INVALID_REQUEST);
    }
  }
}
