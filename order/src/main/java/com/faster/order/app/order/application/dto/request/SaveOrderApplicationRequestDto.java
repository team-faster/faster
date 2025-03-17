package com.faster.order.app.order.application.dto.request;

import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.entity.OrderItem;
import com.faster.order.app.order.domain.entity.OrdererInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SaveOrderApplicationRequestDto(
    UUID supplierCompanyId,
    String supplierCompanyName,
    UUID receivingCompanyId,
    String receivingCompanyName,
    String receivingCompanyAddress,
    String receivingCompanyContact,
    String request,
    List<SaveOrderItemApplicationRequestDto> orderItems
) {

  public Order toEntity() {

    Order order = Order.of(supplierCompanyId, receivingCompanyId, supplierCompanyName, request);
    OrdererInfo ordererInfo = OrdererInfo.of(receivingCompanyName, receivingCompanyAddress,
        receivingCompanyContact);
    order.linkOrdererInfo(ordererInfo);

    this.orderItems.stream()
        .map(it -> it.toEntityWithOrder(order))
        .toList();

    return order;
  }

  @Builder
  public record SaveOrderItemApplicationRequestDto(
      UUID productId,
      String name,
      BigDecimal price,
      Integer quantity
  ) {

    OrderItem toEntityWithOrder(Order order) {
      OrderItem orderItem = this.toEntity();
      orderItem.linkWithOrder(order);
      return orderItem;
    }

    OrderItem toEntity() {
      return OrderItem.of(productId, name, price, quantity);
    }
  }
}
