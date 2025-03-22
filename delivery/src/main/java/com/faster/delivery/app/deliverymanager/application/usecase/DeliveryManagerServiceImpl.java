package com.faster.delivery.app.deliverymanager.application.usecase;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.deliverymanager.application.HubClient;
import com.faster.delivery.app.deliverymanager.application.UserClient;
import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationResponse;
import com.faster.delivery.app.deliverymanager.application.dto.AssignDeliveryManagerApplicationRequestDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.application.dto.UserDto;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager.Type;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import com.faster.delivery.app.global.exception.DeliveryManagerErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

  @Override
  @Transactional
  public Long saveDeliveryManager(DeliveryManagerSaveDto saveDto) {
    // 허브 조건 조회
    List<HubDto> hubListData = hubClient.getHubListData(List.of(saveDto.hubId()));
    HubDto hubData = hubListData.get(0);

    // 유저 정보 조회
    UserDto userData = userClient.getUserData(saveDto.userId());

    // 배송 기사 정보 구성 및 save
    DeliveryManager deliveryManager = DeliveryManager.builder()
        .id(userData.userId())
        .userName(userData.name())
        .hubId(hubData.hubId())
        .type(getDeliveryManagerTypeByString(saveDto.type()))
        .deliverySequenceNumber(1) // TODO : 동시성 고려 처리
        .build();

    DeliveryManager savedDeliveryManager = deliveryManagerRepository.save(deliveryManager);
    return savedDeliveryManager.getId();
  }

  @Override
  public DeliveryManagerDetailDto getDeliveryManagerDetail(CurrentUserInfoDto userInfo, Long deliveryManagerId) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfo, deliveryManager.getId(), deliveryManager);

    // dto 변환
    DeliveryManagerDetailDto deliveryManagerDetailDto = DeliveryManagerDetailDto.from(deliveryManager);
    return deliveryManagerDetailDto;
  }

  @Override
  @Transactional
  public Long updateDeliveryManager(Long deliveryManagerId,
      DeliveryManagerUpdateDto updateDto, CurrentUserInfoDto userInfo) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfo, deliveryManager.getId(), deliveryManager);

    Type newType = getDeliveryManagerTypeByString(updateDto.type());

    // update
    deliveryManager.update(newType, updateDto.deliverySequenceNumber());
    return deliveryManager.getId();
  }

  @Override
  @Transactional
  public Long deleteDeliveryManager(Long deliveryManagerId, CurrentUserInfoDto userInfo) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfo, deliveryManager.getId(), deliveryManager);

    // delete
    deliveryManager.delete(LocalDateTime.now(), userInfo.userId());
    return deliveryManager.getId();
  }

  @Override
  public DeliveryManagerDetailDto getDeliveryManagerDetailInternal(CurrentUserInfoDto userInfo, Long deliveryManagerId) {
    DeliveryManager deliveryManager = deliveryManagerRepository
        .findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfo, deliveryManager.getId(), deliveryManager);

    // dto 변환
    DeliveryManagerDetailDto deliveryManagerDetailDto = DeliveryManagerDetailDto.from(deliveryManager);
    return deliveryManagerDetailDto;
  }

  @Override
  public DeliveryManagerDetailDto getDeliveryManagerByUserIdInternal(
      CurrentUserInfoDto userInfo, Long userId) {

    DeliveryManager deliveryManager = deliveryManagerRepository.findByIdAndDeletedAtIsNull(userId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfo, deliveryManager.getId(), deliveryManager);

    return DeliveryManagerDetailDto.from(deliveryManager);
  }

  @Override
  public AssignDeliveryManagerApplicationResponse assignDeliveryManger(
      AssignDeliveryManagerApplicationRequestDto dto) {

    // 첫번째 담당자를 지정할 현재 시퀀스
    Long firstTotalAssignSequence = deliveryManagerRepository
            .incrementManagerSequenceByCompanyId(
                dto.hubId(), dto.type(), dto.requiredAssignManagerCount()
            ) - dto.requiredAssignManagerCount() + 1;

    // 배송 매니저 담당자 명수
    long assignableManagerCount = deliveryManagerRepository.countByHubIdAndType(
        dto.hubId(), Type.valueOf(dto.type().name()));

    if(assignableManagerCount == 0)
      throw new CustomException(DeliveryManagerErrorCode.NOT_FOUND);

    // 배정된 시퀀스 배송 담당자들
    Set<Integer> seqs = getAssignManagerSequences(
        firstTotalAssignSequence.intValue(), (int) assignableManagerCount, dto.requiredAssignManagerCount());
    List<DeliveryManager> deliveryManagers = deliveryManagerRepository.findAllByHubIdAndTypeAndDeliverySequenceNumber(
        dto.hubId(), Type.COMPANY_DELIVERY, seqs);

    // requiredAssignManagerCount 만큼 List 만들기
    Map<Integer, DeliveryManager> deliveryManagerSequnceMap = deliveryManagers.stream()
        .collect(Collectors.toMap(DeliveryManager::getDeliverySequenceNumber,
            Function.identity()));
    List<DeliveryManager> assignedDeliveryManager = new ArrayList<>();
    for(int i=0 ; i<assignableManagerCount ; i++){
      assignedDeliveryManager.add(deliveryManagerSequnceMap.get((firstTotalAssignSequence + i - 1) % assignableManagerCount));
    }

    return AssignDeliveryManagerApplicationResponse.from(assignedDeliveryManager);
  }

  private static Set<Integer> getAssignManagerSequences(
      int firstTotalAssignSequence, int assignableManagerCount, int requiredAssignManagerCount) {
//    curSequence - 1 % deliveryManagerCountByHubIdAndType + 1;
    return IntStream.range(0, requiredAssignManagerCount)
        .map(i -> (firstTotalAssignSequence + i - 1) % assignableManagerCount + 1)
        .boxed()
        .collect(Collectors.toSet());
  }

  private void checkRole(
      CurrentUserInfoDto userInfo, Long deliveryManagerId, DeliveryManager deliveryManager) {

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
