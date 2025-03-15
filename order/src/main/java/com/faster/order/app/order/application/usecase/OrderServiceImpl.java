package com.faster.order.app.order.application.usecase;

import com.common.exception.CustomException;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public OrderDetailApplicationResponseDto getOrderById(UUID orderId) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 - 모든 주문 조회 가능, 업체 담당자 - 해당 업체 주문 조회 가능

    Order order = orderRepository.findByIdAndDeletedAtIsNullFetchJoin(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    return OrderDetailApplicationResponseDto.from(order);
  }

  @Override
  public void deleteOrderById(UUID orderId) {
    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 삭제는 완료 혹은 취소 상태인 주문에만 가능
    // 마스터 - 모든 업체 주문 삭제 가능, 업체 담당자 - 해당 업체 주문 삭제 가능

    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    if (!order.isPossibleToDelete()) {
      throw new CustomException(OrderErrorCode.UNABLE_REMOVE);
    }
    order.delete(LocalDateTime.now(), 1L);
  }
}
