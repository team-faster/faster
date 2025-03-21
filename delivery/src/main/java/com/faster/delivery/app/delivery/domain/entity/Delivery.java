package com.faster.delivery.app.delivery.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_delivery")
@Entity
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private UUID orderId;
  private UUID companyDeliveryManagerId;
  private UUID sourceHubId;
  private UUID destinationHubId;
  private UUID receiptCompanyId;
  private String receiptCompanyAddress;
  private String recipientName;
  private String recipientSlackId;

  @Enumerated(EnumType.STRING)
  private Status status = Status.READY;

  @ToString.Exclude
  @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<DeliveryRoute> deliveryRouteList = new ArrayList<>();

  public enum Status {
    READY,
    DISPATCHED, // 배송 시작
    INPROGRESS, // 진행중
    DELIVERED;  // 배달 완료


    public static Status fromString(String search) {
      if (search == null) {
        return null;
      }
      return Status.valueOf(search.toUpperCase());
    }
  }

  @Builder
  private Delivery(
      UUID orderId,
      UUID companyDeliveryManagerId,
      UUID sourceHubId,
      UUID destinationHubId,
      UUID receiptCompanyId,
      String receiptCompanyAddress,
      String recipientName,
      String recipientSlackId,
      List<DeliveryRoute> deliveryRouteList,
      Status status) {
    this.orderId = orderId;
    this.companyDeliveryManagerId = companyDeliveryManagerId;
    this.sourceHubId = sourceHubId;
    this.destinationHubId = destinationHubId;
    this.receiptCompanyId = receiptCompanyId;
    this.receiptCompanyAddress = receiptCompanyAddress;
    this.recipientName = recipientName;
    this.recipientSlackId = recipientSlackId;
    this.deliveryRouteList = deliveryRouteList;
    this.status = status;
    addDeliveryRouteList(deliveryRouteList);
  }

  public void addDeliveryRouteList(List<DeliveryRoute> deliveryRouteList) {
    for (DeliveryRoute deliveryRoute : deliveryRouteList) {
      deliveryRoute.bindDelivery(this);
    }
    this.deliveryRouteList.addAll(deliveryRouteList);
  }

  public void updateStatus(Status status) {
    this.status = status;
  }
}
