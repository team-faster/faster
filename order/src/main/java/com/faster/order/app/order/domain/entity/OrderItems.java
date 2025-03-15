package com.faster.order.app.order.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
public class OrderItems {

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  public void addItem(OrderItem orderItem) {
    this.orderItems.add(orderItem);
  }

  public String getOrderName() {
    String name = orderItems.get(0).getName();
    if (orderItems.size() > 1) {
      name += " 외 " + orderItems.size() + "건";
    }
    return name;
  }

  public BigDecimal getTotalPrice() {
    return orderItems.stream()
        .map(OrderItem::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Stream<OrderItem> stream() {
    return orderItems.stream();
  }
}
