package com.faster.delivery.app.deliverymanager.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_delivery_manager")
@Entity
public class DeliveryManager extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  private Long id; // User.id 와 동일한 값

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "hub_id")
  private UUID hubId;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "delivery_sequence_number", nullable = false)
  private Integer deliverySequenceNumber;

  public enum Type {
    HUB_DELIVERY,
    COMPANY_DELIVERY
  }

  public void update(Type type, Integer deliverySequenceNumber, UUID hubId) {
    this.type = type;
    this.deliverySequenceNumber = deliverySequenceNumber;
    this.hubId = hubId;
  }
}
