package com.faster.delivery.app.deliverymanager.domain.entity;

import com.common.domain.BaseEntity;
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
  private Long id; // User.id 와 동일한 값
  private String userName;
  private UUID hubId;
  @Enumerated(EnumType.STRING)
  private Type type;
  private Integer deliverySequenceNumber;

  public enum Type {
    HUB_DELIVERY,
    COMPANY_DELIVERY
  }

  public void update(Type type, Integer deliverySequenceNumber) {
    this.type = type;
    this.deliverySequenceNumber = deliverySequenceNumber;
  }
}
