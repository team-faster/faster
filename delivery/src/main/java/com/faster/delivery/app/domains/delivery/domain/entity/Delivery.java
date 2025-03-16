package com.faster.delivery.app.domains.delivery.domain.entity;

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
@Table(name = "p_delivery")
@Entity
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  public enum Status {
    INPROGRESS,
    SHIPPED,
    DELIVERED,
    READY
  }

  public void addDeliveryRouteList(List<DeliveryRoute> deliveryRouteList) {
    for (DeliveryRoute deliveryRoute : deliveryRouteList) {
      deliveryRoute.bindDelivery(this);
    }
    this.deliveryRouteList.addAll(deliveryRouteList);
  }
}
