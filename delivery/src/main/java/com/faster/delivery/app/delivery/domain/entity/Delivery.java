package com.faster.delivery.app.delivery.domain.entity;

import com.common.domain.BaseEntity;
import com.common.exception.CustomException;
import com.common.exception.type.ApiErrorCode;
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
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
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
  private Status status;

  @Builder.Default
  @ToString.Exclude
  @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<DeliveryRoute> deliveryRouteList = new ArrayList<>();

  @RequiredArgsConstructor
  public enum Status {
    READY(0, (status) -> status.order >= 0 || status.order == -100),
    DISPATCHED(1, (status) -> status.order >= 1), // 배송 시작
    INPROGRESS(2, (status) -> status.order >= 2), // 진행중
    DELIVERED(3, (status) -> status.order >= 3),  // 배달 완료
    CANCELED(-100, (status) -> false),;

    private final int order;
    private final Predicate<Status> transitionRule;

    public boolean isPossibleToUpdate(Status status) {
      return transitionRule.test(status);
    }

    public boolean isOrderUpdateRequired() {
      return this == Status.DELIVERED || this == Status.DISPATCHED;
    }

  }

  public void addDeliveryRouteList(List<DeliveryRoute> deliveryRouteList) {
    for (DeliveryRoute deliveryRoute : deliveryRouteList) {
      deliveryRoute.bindDelivery(this);
    }
    this.deliveryRouteList.addAll(deliveryRouteList);
  }

  public void updateStatus(Status status) {
    if (!this.status.isPossibleToUpdate(status)) {
      throw new CustomException(ApiErrorCode.INVALID_REQUEST);
    }
    this.status = status;
  }
}
