package com.faster.order.app.order.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Integer quantity;

  @Builder
  private OrderItem(Order order, UUID productId, String name,
      BigDecimal price, Integer quantity) {
    this.order = order;
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public static OrderItem of(UUID productId, String name, BigDecimal price, Integer quantity) {
    return OrderItem.builder()
        .productId(productId)
        .name(name)
        .price(price)
        .quantity(quantity)
        .build();
  }

  public void linkWithOrder(Order order) {
    this.order = order;
    this.order.getOrderItems().addItem(this);
  }

  public void softDelete(LocalDateTime now, Long userId) {
    super.delete(now, userId);
  }
}
