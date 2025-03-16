package com.faster.delivery.app.domains.delivery.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_delivery_route")
@Entity
public class DeliveryRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ToString.Exclude
  @JoinColumn(name = "p_delivery_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Delivery delivery;

  private Integer sequence;

  private UUID sourceHubId;
  private UUID destinationHubId;

  private Long expectedDistanceM;
  private Long expectedTimeMin;

  private Long realDistanceM;
  private Long realTimeMin;

  @Enumerated(EnumType.STRING)
  private Type type;
  @Enumerated(EnumType.STRING)
  private Status status;

  private UUID deliveryManagerId;
  private String deliveryManagerName;

  public enum Type {
    TO_HUB,
    TO_COMPANY
  }

  public enum Status {
    PENDING_TRANSFER,  // 허브 이동 대기중
    IN_TRANSIT_TO_HUB, // 허브 이동 중
    ARRIVED_AT_HUB,    // 목적지 허브 도착
    OUT_FOR_DELIVERY,  // 배송 중
    DELIVERED,         // 배송 완료
    RETURNED,          // 반품됨
    CANCELLED          // 배송 취소
  }

  public void bindDelivery(Delivery delivery) {
    this.delivery = delivery;
  }
}
