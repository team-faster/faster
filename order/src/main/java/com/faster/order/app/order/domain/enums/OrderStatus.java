package com.faster.order.app.order.domain.enums;

import com.common.exception.CustomException;
import com.faster.order.app.global.exception.OrderErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  ACCEPTED(1),
  CONFIRMED(2),
  DISPATCHED(3),
  DELIVERED(4),
  COMPLETED(5),
  CANCELED(-100);

  private final Integer order;

  public static OrderStatus parse(String status) {
    try {
      return OrderStatus.valueOf(status);
    } catch (IllegalArgumentException e) {
      throw new CustomException(OrderErrorCode.INVALID_STATUS);
    }
  }

  public boolean isValidToUpdate() {
    return !Set.of(OrderStatus.ACCEPTED, OrderStatus.CONFIRMED, OrderStatus.CANCELED).contains(this);
  }

  public boolean isPossibleToProceed(OrderStatus currStatus) {
    // 현재 상태보다 이후의 상태로 변경하는 것이 맞는지 확인
    return currStatus.getOrder().compareTo(this.getOrder()) <= 0;
  }
}
