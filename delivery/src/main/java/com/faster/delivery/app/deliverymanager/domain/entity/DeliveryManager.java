package com.faster.delivery.app.deliverymanager.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class DeliveryManager {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Long userId;
  private String userName;
  private UUID hubId;
  @Enumerated(EnumType.STRING)
  private Type type;
  private Integer deliverySequenceNumber;

  public enum Type {
    HUB_DELIVERY,
    COMPANY_DELIVERY
  }
}
