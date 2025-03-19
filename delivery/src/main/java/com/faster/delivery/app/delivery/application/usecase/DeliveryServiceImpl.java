package com.faster.delivery.app.delivery.application.usecase;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.delivery.app.delivery.application.CompanyClient;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.HubClient;
import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final CompanyClient companyClient;
  private final HubClient hubClient;
  private final DeliveryManagerClient deliveryManagerClient;

  @Transactional
  public UUID saveDelivery(DeliverySaveDto deliverySaveDto) {

    // TODO : Client 예외 처리 로직 추가 예정
    // 수취 업체 정보 조회
    CompanyDto companyData = companyClient.getCompanyData(deliverySaveDto.receiveCompanyId());

    // 허브 경로 조회
    List<HubRouteDto> hubRouteDataList = hubClient.getHubRouteDataList(
        deliverySaveDto.sourceHubId(), deliverySaveDto.destinationHubId());
    // 배송 경로 목록 구성
    List<DeliveryRoute> deliveryRouteList = hubRouteDataList.stream()
        .map(HubRouteDto::toDeliveryRoute)
        .toList();

    // 배송 정보 구성
    Delivery delivery = Delivery.builder()
        .orderId(deliverySaveDto.orderId())
        .sourceHubId(deliverySaveDto.sourceHubId())
        .destinationHubId(deliverySaveDto.destinationHubId())
        .receiptCompanyId(deliverySaveDto.receiveCompanyId())
        .receiptCompanyAddress(companyData.address())
        .recipientName(companyData.companyManagerName())
        .recipientSlackId(companyData.companyManagerSlackId())
        .status(Status.READY)
        .build();
    delivery.addDeliveryRouteList(deliveryRouteList);

    // save
    Delivery savedDelivery = deliveryRepository.save(delivery);

    // TODO : 배송 기사 배정 로직 구현

    // TODO : 주문 상태 업데이트

    return savedDelivery.getId();
  }

  public void getDeliveryDetail(UUID deliveryId, Long userId) {
    // 배송 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // TODO : 권한 체크

    // 마스터 : 전부 조회 가능
    // 허브 담당자 : 소스허브 or 목적지 허브 인 배송만 조회
    UUID sourceHubId = delivery.getSourceHubId();
    UUID destinationHubId = delivery.getDestinationHubId();
    // 유저 정보 조회
    // TODO : 허브 : 허브 정보(허브담당자 정보 포함) 조회

    // 배송 담당자 : 본인이 배송담당자인 배송만 조회
    UUID companyDeliveryManagerId = delivery.getCompanyDeliveryManagerId();
    // 배송 담당자 정보 조회
    DeliveryManagerDto deliveryManagerData =
        deliveryManagerClient.getDeliveryManagerData(companyDeliveryManagerId);
    // TODO : 권한 체크

    // 업체 담당자 : 본인이 수령인인 배송만 조회
    UUID receiptCompanyId = delivery.getReceiptCompanyId();
    // TODO : 업체: 업체 -> 업체 담당자 정보 조회
    CompanyDto companyData = companyClient.getCompanyData(receiptCompanyId);
    Long companyManagerId = companyData.companyManagerUserId();
    // TODO : 권한 체크

    // TODO : dto 변환
//    DeliveryDetailDto.of(
//        delivery, deliveryManagerData.deliveryManagerName(),

    // TODO : return
  }

  @Transactional
  public UUID updateDeliveryStatus (CurrentUserInfoDto userInfoDto, UUID deliveryId,
      DeliveryUpdateDto deliveryUpdateDto) {

    // 배송 정보 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // TODO : 권한 검사
    switch (userInfoDto.role()) {
      case ROLE_DELIVERY -> {
        DeliveryManagerDto deliveryManagerData = deliveryManagerClient.getDeliveryManagerData(
            delivery.getCompanyDeliveryManagerId()
        );
        if (!deliveryManagerData.userId().equals(userInfoDto.userId())) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
      case ROLE_HUB -> {
        // TODO : source, destination 허브 정보 조회 후 userId 비교
      }
    }

    // 배송 정보 업데이트
    Status deliveryStatus = getDeliveryStatusByString(deliveryUpdateDto.status());
    delivery.updateStatus(deliveryStatus);

    return delivery.getId();
  }

  @Transactional
  public UUID deleteDelivery(UUID deliveryId, CurrentUserInfoDto userInfoDto) {
    // 배송 정보 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // TODO : 권한 검사
    if (UserRole.ROLE_HUB.equals(userInfoDto.role())) {
      // TODO : source, destination 허브 정보 조회 후 userId 비교
    }

    // 삭제
    delivery.delete(LocalDateTime.now(), userInfoDto.userId());

    return delivery.getId();
  }

  @Transactional
  public UUID saveDeliveryInternal(DeliverySaveDto deliverySaveDto) {

    // TODO : Client 예외 처리 로직 추가 예정
    // 수취 업체 정보 조회
    CompanyDto companyData = companyClient.getCompanyData(deliverySaveDto.receiveCompanyId());

    // 허브 경로 조회
    List<HubRouteDto> hubRouteDataList = hubClient.getHubRouteDataList(
        deliverySaveDto.sourceHubId(), deliverySaveDto.destinationHubId());
    // 배송 경로 목록 구성
    List<DeliveryRoute> deliveryRouteList = hubRouteDataList.stream()
        .map(HubRouteDto::toDeliveryRoute)
        .toList();

    // 배송 정보 구성
    Delivery delivery = Delivery.builder()
        .orderId(deliverySaveDto.orderId())
        .sourceHubId(deliverySaveDto.sourceHubId())
        .destinationHubId(deliverySaveDto.destinationHubId())
        .receiptCompanyId(deliverySaveDto.receiveCompanyId())
        .receiptCompanyAddress(companyData.address())
        .recipientName(companyData.companyManagerName())
        .recipientSlackId(companyData.companyManagerSlackId())
        .status(Status.READY)
        .build();
    delivery.addDeliveryRouteList(deliveryRouteList);

    // save
    Delivery savedDelivery = deliveryRepository.save(delivery);

    // TODO : 배송 기사 배정 로직 구현

    return savedDelivery.getId();
  }

  @Transactional
  public UUID updateDeliveryStatusInternal(
      UUID deliveryId, DeliveryUpdateDto deliveryUpdateDto, CurrentUserInfoDto userInfoDto) {

    // 배송 정보 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // TODO : 권한 검사
    switch (userInfoDto.role()) {
      case ROLE_COMPANY -> {
        // TODO : 업체 매니저 정보 조회
        UUID receiptCompanyId = delivery.getReceiptCompanyId();
        CompanyDto companyData = companyClient.getCompanyData(receiptCompanyId);
        if (!userInfoDto.userId().equals(companyData.companyManagerUserId())) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
      case ROLE_HUB -> {
        // TODO : source, destination 허브 정보 조회 후 userId 비교
      }
    }

    // 배송 정보 업데이트
    Status deliveryStatus = getDeliveryStatusByString(deliveryUpdateDto.status());
    delivery.updateStatus(deliveryStatus);

    // TODO : 주문 정보 업데이트

    return delivery.getId();
  }

  private Status getDeliveryStatusByString(String statusString) {
    try {
      return Status.valueOf(statusString);
    } catch (Exception e) {
      throw new CustomException(ApiErrorCode.INVALID_REQUEST);
    }
  }
}
