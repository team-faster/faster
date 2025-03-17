package com.faster.order.app.order.fixture;

import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.entity.OrderItem;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class OrderFixture {

  public static Order createOrder(UUID supplierCompanyId, UUID receivingCompanyId, UUID deliveryId, int orderItemCnt) {

    Order order = Order.of(supplierCompanyId, receivingCompanyId, deliveryId,
        "공급업체명", "test request", OrderStatus.ACCEPTED);
    List<OrderItem> orderItems = IntStream.range(0, orderItemCnt)
        .mapToObj(i -> {
          OrderItem orderItem = OrderItem.of(UUID.randomUUID(), "아이템" + i,
              BigDecimal.valueOf(10000), i);
          orderItem.linkWithOrder(order);
          return orderItem;
        })
        .toList();
    order.assignName();
    order.calcTotalPrice();
    return order;
  }
}
