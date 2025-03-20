package com.faster.delivery.app.delivery.application.usecase;

import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.delivery.app.delivery.application.CompanyClient;
import com.faster.delivery.app.delivery.application.DeliveryManagerClient;
import com.faster.delivery.app.delivery.application.HubClient;
import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryDetailDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryManagerDto;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveApplicationDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import com.faster.delivery.app.delivery.application.dto.HubRouteDto;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.domain.repository.DeliveryRepository;
import com.faster.delivery.app.deliverymanager.application.MessageClient;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.application.dto.SendMessageApplicationRequestDto;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final CompanyClient companyClient;
  private final HubClient hubClient;
  private final DeliveryManagerClient deliveryManagerClient;
  private final MessageClient messageClient;

  @Transactional
  public UUID saveDelivery(DeliverySaveApplicationDto deliverySaveDto) {

//    // TODO : Client 예외 처리 로직 추가 예정
//    // 수취 업체 정보 조회
//    CompanyDto companyData = companyClient.getCompanyData(deliverySaveDto.receiveCompanyId());
//
//    // 허브 경로 조회
//    List<HubRouteDto> hubRouteDataList = hubClient.getHubRouteDataList(
//        deliverySaveDto.sourceHubId(), deliverySaveDto.destinationHubId());
//
//    // 배송 경로 목록 구성
//    List<DeliveryRoute> deliveryRouteList = hubRouteDataList.stream()
//        .map(HubRouteDto::toDeliveryRoute)
//        .toList();
//
//    // 배송 정보 구성
//    Delivery delivery =
//        Delivery.builder()
//        .orderId(deliverySaveDto.orderId())
//        .sourceHubId(deliverySaveDto.sourceHubId())
//        .destinationHubId(deliverySaveDto.destinationHubId())
//        .receiptCompanyId(deliverySaveDto.receiveCompanyId())
//        .receiptCompanyAddress(companyData.address())
//        .recipientName(companyData.companyManagerName())
//        .recipientSlackId(companyData.companyManagerSlackId())
//        .status(Status.READY)
//        .build();
//    delivery.addDeliveryRouteList(deliveryRouteList);
//
//    // save
//    Delivery savedDelivery = deliveryRepository.save(delivery);
//
//    // TODO : 배송 기사 배정 로직 구현
//
//    // TODO : 주문 상태 업데이트
//
//    return savedDelivery.getId();
    return null;
  }

  public DeliveryDetailDto getDeliveryDetail(UUID deliveryId, CurrentUserInfoDto userInfoDto) {
    // 배송 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    // 권한 체크
    checkRole(userInfoDto, delivery);

    // 배송 담당자 정보 조회
    DeliveryManagerDto deliveryManagerData = deliveryManagerClient.getDeliveryManagerData(
        delivery.getCompanyDeliveryManagerId());

    // 허브 정보 조회
    List<HubDto> hubListData = hubClient.getHubListData(
        List.of(delivery.getSourceHubId(), delivery.getDestinationHubId()));
    Map<UUID, String> hubIdNameMap = hubListData.stream()
        .collect(Collectors.toMap(hubDto -> hubDto.hubId(), hubDto -> hubDto.name()));

    // 업체 정보 조회
    CompanyDto companyData = companyClient.getCompanyData(delivery.getReceiptCompanyId());

    // dto 변환
    DeliveryDetailDto deliveryDetailDto = DeliveryDetailDto.of(
        delivery, deliveryManagerData.deliveryManagerName(),
        hubIdNameMap.get(delivery.getSourceHubId()),
        hubIdNameMap.get(delivery.getDestinationHubId()),
        companyData.name());

    return deliveryDetailDto;
  }

//  public void getDeliveryList(Pageable pageable, String search, CurrentUserInfoDto userInfoDto) {
//
//    Page<Delivery> searchResult = null;
//    switch (userInfoDto.role()) {
//      case ROLE_COMPANY -> {
//
//      }
//      case ROLE_DELIVERY -> {
//
//      }
//      case ROLE_HUB -> {
//
//      }
//      case ROLE_MASTER -> {
//
//      }
//    }
//  }

  @Transactional
  public UUID updateDeliveryStatus (CurrentUserInfoDto userInfoDto, UUID deliveryId,
      DeliveryUpdateDto deliveryUpdateDto) {

    // 배송 정보 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfoDto, delivery);

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

    checkRole(userInfoDto, delivery);

    // 삭제
    delivery.delete(LocalDateTime.now(), userInfoDto.userId());

    return delivery.getId();
  }

  @Transactional
  public UUID saveDeliveryInternal(DeliverySaveApplicationDto deliverySaveDto) {

    // TODO : Client 예외 처리 로직 추가 예정
    // 업체 정보 조회
    CompanyDto supplierCompany = companyClient.getCompanyData(deliverySaveDto.supplierCompanyId());
    CompanyDto receiveCompany = companyClient.getCompanyData(deliverySaveDto.receiveCompanyId());

    // 허브 경로 조회
    List<HubRouteDto> hubRouteDataList = hubClient.getHubRouteDataList(
        supplierCompany.hubId(), receiveCompany.hubId());

    // 배송 경로 목록 구성
    List<DeliveryRoute> deliveryRouteList = hubRouteDataList.stream()
        .map(HubRouteDto::toDeliveryRoute).toList();

    // 업체 배송 담당자 지정
    DeliveryManagerDto deliveryManagerDto = deliveryManagerClient.assignCompanyDeliveryManager(
        receiveCompany.id());

    ArrayList<UUID> routeIds = new ArrayList<>();
    routeIds.add(supplierCompany.hubId());
    for(DeliveryRoute route : deliveryRouteList){
      routeIds.add(route.getDestinationHubId());
    }

    Delivery savedDelivery = saveDelivery(deliverySaveDto,
        deliveryManagerDto, supplierCompany, receiveCompany, deliveryRouteList);

    List<HubDto> hubListData = hubClient.getHubListData(routeIds);

    sendMessage(hubListData, supplierCompany.hubId(), receiveCompany.hubId(),
        savedDelivery, deliveryManagerDto.deliveryManagerName());

    return savedDelivery.getId();
  }

  private Delivery saveDelivery(DeliverySaveApplicationDto deliverySaveDto,
      DeliveryManagerDto deliveryManagerDto, CompanyDto supplierCompany, CompanyDto receiveCompany,
      List<DeliveryRoute> deliveryRouteList) {
    Delivery delivery = Delivery.builder()
        .orderId(deliverySaveDto.orderId())
        .companyDeliveryManagerId(deliveryManagerDto.deliveryManagerId())
        .sourceHubId(supplierCompany.hubId())
        .destinationHubId(receiveCompany.hubId())
        .receiptCompanyId(receiveCompany.id())
        .receiptCompanyAddress(receiveCompany.address())
        .recipientName(receiveCompany.companyManagerName())
        .recipientSlackId(receiveCompany.companyManagerSlackId())
        .deliveryRouteList(deliveryRouteList)
        .build();

    // save
    Delivery savedDelivery = deliveryRepository.save(delivery);
    return savedDelivery;
  }

  // 이벤트 리스너로 변경하면 좋을 듯
  private void sendMessage(List<HubDto> hubListData, UUID supplierCompanyId,
      UUID receiveCompanyId, Delivery savedDelivery, String deliveryManagerName) {
    StringBuilder waypoints = new StringBuilder("|");
    String hubSource = null;
    String hubDestination = null;
    for(HubDto hubDto : hubListData){
      UUID hubId = hubDto.hubId();
      if(hubId.equals(supplierCompanyId)) {
        hubSource = hubDto.name();
        continue;
      }
      if(hubId.equals(receiveCompanyId)) {
        hubDestination = hubDto.name();
        continue;
      }
      waypoints.append(hubDto.name()).append("|");
    }

    // 메시지 전송
    messageClient.sendMessage(
        SendMessageApplicationRequestDto.builder()
            .deliveryId(savedDelivery.getId())
            .orderId(savedDelivery.getOrderId())
            .orderUserName(savedDelivery.getRecipientName())
            .orderUserSlackId(savedDelivery.getRecipientSlackId())
            .hubSource(hubSource)
            .hubWaypoint(waypoints.toString())
            .hubDestination(hubDestination)
            .deliveryManager(deliveryManagerName)
            .build());
  }

  @Transactional
  public UUID updateDeliveryStatusInternal(
      UUID deliveryId, DeliveryUpdateDto deliveryUpdateDto, CurrentUserInfoDto userInfoDto) {

    // 배송 정보 조회
    Delivery delivery = deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
        .orElseThrow(() -> new CustomException(ApiErrorCode.NOT_FOUND));

    checkRole(userInfoDto, delivery);

    // 배송 정보 업데이트
    Status deliveryStatus = getDeliveryStatusByString(deliveryUpdateDto.status());
    delivery.updateStatus(deliveryStatus);

    // TODO : 주문 정보 업데이트

    return delivery.getId();
  }

  private void checkRole(CurrentUserInfoDto userInfoDto, Delivery delivery) {
    switch (userInfoDto.role()) {
      case ROLE_COMPANY -> { // 업체 담당자 : 본인이 수령인인 배송만 조회
        UUID receiptCompanyId = delivery.getReceiptCompanyId();
        // 업체 담당자 정보 조회
        CompanyDto companyData = companyClient.getCompanyData(receiptCompanyId);
        Long companyManagerId = companyData.companyManagerUserId();
        if (!userInfoDto.userId().equals(companyManagerId)) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
      case ROLE_DELIVERY -> { // 배송 담당자 : 본인이 배송담당자인 배송만 조회
        Long companyDeliveryManagerId = delivery.getCompanyDeliveryManagerId();
        // 배송 담당자 정보 조회
        DeliveryManagerDto deliveryManagerData =
            deliveryManagerClient.getDeliveryManagerData(companyDeliveryManagerId);
        if (userInfoDto.userId().equals(deliveryManagerData.userId())) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
      case ROLE_HUB -> { // 허브 담당자 : 소스허브 or 목적지 허브 인 배송만 조회
        UUID sourceHubId = delivery.getSourceHubId();
        UUID destinationHubId = delivery.getDestinationHubId();
        // 허브 정보(허브담당자 정보 포함) 조회
        List<HubDto> hubListData = hubClient.getHubListData(List.of(sourceHubId, destinationHubId));
        List<Long> hubManagerIdList = hubListData.stream().map(HubDto::hubManagerId).toList();
        if (!hubManagerIdList.contains(userInfoDto.userId())) {
          throw new CustomException(ApiErrorCode.UNAUTHORIZED);
        }
      }
    }
  }

  private Status getDeliveryStatusByString(String statusString) {
    try {
      return Status.valueOf(statusString);
    } catch (Exception e) {
      throw new CustomException(ApiErrorCode.INVALID_REQUEST);
    }
  }
}
