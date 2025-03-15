package com.faster.order.app.order.domain.entity;

import com.common.domain.BaseEntity;
import com.common.exception.CustomException;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.domain.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID supplierCompanyId;

  @Column(nullable = false)
  private UUID receivingCompanyId;

  @Column
  private UUID deliveryId;

  @Column(nullable = false, length = 100)
  private String supplierCompanyName;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  @Column(length = 500)
  private String request;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private OrdererInfo ordererInfo;

  @Embedded
  private OrderItems orderItems;

  @Builder
  private Order(UUID supplierCompanyId, UUID receivingCompanyId, UUID deliveryId,
      String supplierCompanyName, String name, BigDecimal totalPrice,
      String request, OrderStatus status, OrdererInfo ordererInfo, OrderItems orderItems) {
    this.supplierCompanyId = supplierCompanyId;
    this.receivingCompanyId = receivingCompanyId;
    this.deliveryId = deliveryId;
    this.supplierCompanyName = supplierCompanyName;
    this.name = name;
    this.totalPrice = totalPrice;
    this.request = request;
    this.status = status;
    this.ordererInfo = ordererInfo;
    this.orderItems = orderItems;
  }

  public static Order of(UUID supplierCompanyId, UUID receivingCompanyId, UUID deliveryId,
      String supplierCompanyName, String request, OrderStatus status) {
    return Order.builder()
        .supplierCompanyId(supplierCompanyId)
        .receivingCompanyId(receivingCompanyId)
        .deliveryId(deliveryId)
        .supplierCompanyName(supplierCompanyName)
        .request(request)
        .status(status)
        .orderItems(new OrderItems())
        .build();
  }

  public void assignName() {
    this.name = this.orderItems.getOrderName();
  }

  public void calcTotalPrice() {
    this.totalPrice = this.orderItems.getTotalPrice();

    if (!isValidTotalPrice()) {
      throw new CustomException(OrderErrorCode.NOT_ENOUGH_TOTAL_PRICE);
    }
  }

  public void orderCancel() {
    this.status = OrderStatus.CANCELED;
  }

  private boolean isValidTotalPrice() {
    return this.totalPrice.compareTo(BigDecimal.valueOf(3000)) >= 0;
  }

  public boolean isPossibleToDelete() {
    return Set.of(OrderStatus.COMPLETED, OrderStatus.CANCELED).contains(this.status);
  }
}
