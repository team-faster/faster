package com.faster.delivery.app.delivery.application.dto;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record DeliveryDetailDto(
  String deliveryId,
  String orderId,
  String companyDeliveryManagerName,
  String status,
  String sourceHubName,
  String destinationHubName,
  String companyName,
  String address,
  String createdAt,
  String updatedAt,
  List<DeliveryRouteDto> deliveryRouteList
) {

  public static DeliveryDetailDto of(
      Delivery delivery,
      String companyDeliveryManagerName,
      String sourceHubName,
      String destinationHubName,
      String receiptCompanyName) {

    ArrayList<DeliveryRouteDto> deliveryRouteDtoList = new ArrayList<>();
    DeliveryDetailDto deliveryDetailDto = DeliveryDetailDto.builder()
        .deliveryId(delivery.getId().toString())
        .orderId(delivery.getOrderId().toString())
        .companyDeliveryManagerName(companyDeliveryManagerName)
        .status(delivery.getStatus().toString())
        .sourceHubName(sourceHubName)
        .destinationHubName(destinationHubName)
        .companyName(receiptCompanyName)
        .address(delivery.getReceiptCompanyAddress())
        .createdAt(delivery.getCreatedAt().toString())
        .updatedAt(delivery.getUpdatedAt().toString())
        .deliveryRouteList(deliveryRouteDtoList)
        .build();

    for (DeliveryRoute deliveryRoute : delivery.getDeliveryRouteList()) {
      DeliveryRouteDto deliveryRouteDto = DeliveryRouteDto.builder()
          .id(deliveryRoute.getId().toString())
          .deliveryId(deliveryRoute.getId().toString())
          .deliveryManagerId(deliveryRoute.getDeliveryManagerId())
          .deliveryManagerName(deliveryRoute.getDeliveryManagerName())
          .sourceHubId(deliveryRoute.getSourceHubId().toString())
          .destinationHubId(deliveryRoute.getDestinationHubId().toString())
          .deliveryManagerName(deliveryRoute.getDeliveryManagerName())
          .sequence(deliveryRoute.getSequence())
          .expectedDistanceM(deliveryRoute.getExpectedDistanceM())
          .expectedTimeMin(deliveryRoute.getExpectedTimeMin())
          .realDistanceM(deliveryRoute.getRealDistanceM())
          .realTimeMin(deliveryRoute.getRealTimeMin())
          .type(deliveryRoute.getType().toString())
          .createdBy(deliveryRoute.getCreatedBy().toString())
          .createdAt(deliveryRoute.getCreatedAt().toString())
          .updatedBy(
              deliveryRoute.getUpdatedBy() == null ? null : deliveryRoute.getUpdatedBy().toString())
          .updatedAt(
              deliveryRoute.getUpdatedAt() == null ? null : deliveryRoute.getUpdatedAt().toString())
          .status(deliveryRoute.getStatus().toString())
          .build();

      deliveryRouteDtoList.add(deliveryRouteDto);
    }
    return deliveryDetailDto;
  }

  @Builder
  public record DeliveryRouteDto(
    String id,
    String deliveryId,
    Long deliveryManagerId,
    String sourceHubId,
    String destinationHubId,
    String deliveryManagerName,
    Integer sequence,
    Long expectedDistanceM,
    Long expectedTimeMin,
    Long realDistanceM,
    Long realTimeMin,
    String type,
    String createdBy,
    String createdAt,
    String updatedBy,
    String updatedAt,
    String status
  ) {

    }
}
/*
"deliveryId": "배송 고유 식별 번호",
"orderId": "주문 ID",
"companyDeliveryManagerName": "업체 배송 담당자 이름",
"status": "배송 상태",
"sourceHubName": "출발지 허브 이름",
"destinationHubName": "목적지 허브 이름",
"companyName": "배송 받는 업체 이름",
"address": "배송 받는 업체 주소"
"createdAt": "생성일자",
"updatedAt": "수정일자"
"deliveryRouteList"  : [
    {
        "id": "PK",
        "deliveryId": "배송 id",
        "deliveryManagerId": "배송담당자 id",
        "sourceHubId": "source 허브 id",
        "destinationHubId": "destination 허브 id",
        "deliveryManagerName": "배송담당자이름",
        "sequence": "경로상순서",
        "expectedDistanceM": "예상거리",
        "expectedTimeMin": "예상소요시간",
        "realDistanceM": "실제거리",
        "realTimeMin": "실제소요시간",
        "type": "배송유형",
        "createdBy": "생성자",
        "createdAt": "생성일자",
        "updatedBy": "수정자",
        "updatedAt": "수정일자",
        "status": "상태"
	   },


 */