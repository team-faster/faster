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

    StringBuilder builder = new StringBuilder().append(orderItems.get(0).getName())
        .append(" x ")
        .append(orderItems.get(0).getQuantity());

    if (orderItems.size() == 1) {
      return builder.toString();
    }

    builder.append(" 외 ")
      .append(orderItems.size() - 1)
      .append("건");
    return builder.toString();
  }

  public BigDecimal getTotalPrice() {
    return orderItems.stream()
        .map(it -> it.getPrice().multiply(BigDecimal.valueOf(it.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Stream<OrderItem> stream() {
    return orderItems.stream();
  }
}
