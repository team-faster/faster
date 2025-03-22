package com.faster.delivery.app.delivery.domain.entity;

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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_delivery_route")
@Entity
public class DeliveryRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
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
  private Long deliveryMangerUserId;
  private String deliveryManagerName;

  public enum Type {
    TO_HUB,
    TO_COMPANY // 사용되는일 없을듯 : 업체배송은 Delivery.Status 를 보고 업체 배송 판단
  }

  public enum Status {
    PENDING,       // 대기중
    INPROGRESS,    //배송 중
    ARRIVED,       // 목적지 도착
    DELIVERED,     // 배송 완료
    CANCELLED     // 배송 취소
  }

  public void bindDelivery(Delivery delivery) {
    this.delivery = delivery;
  }

  public void updateRealMeasurement(Long realDistanceM, Long realTimeMin) {
    this.realDistanceM = realDistanceM;
    this.realTimeMin = realTimeMin;
  }

  public void updateStatus(Status status) {
    this.status = status;
  }

  public void updateManager(Long deliveryManagerUserId, UUID deliveryManagerId, String deliveryManagerName) {
    this.deliveryMangerUserId = deliveryManagerUserId;
    this.deliveryManagerId = deliveryManagerId;
    this.deliveryManagerName = deliveryManagerName;
  }
}
