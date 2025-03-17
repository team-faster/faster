package com.faster.order.app.order.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.application.dto.response.CancelOrderApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.GetOrderDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.InternalConfirmOrderApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.InternalUpdateOrderStatusApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public PageResponse<SearchOrderApplicationResponseDto> getOrdersByCondition(CurrentUserInfoDto userInfo,
      Pageable pageable, SearchOrderConditionDto condition) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 모든 주문 조회 가능
    // 업체 담당자 - 해당 업체 주문만 조회 가능

    UUID companyId = userInfo.role() == UserRole.ROLE_MASTER ? null : UUID.randomUUID();
    Page<SearchOrderApplicationResponseDto> pageList = orderRepository.getOrdersByConditionAndCompanyId(
            pageable, condition.toCriteria(), companyId, userInfo.role())
        .map(SearchOrderApplicationResponseDto::from);
    return PageResponse.from(pageList);
  }

  @Override
  public GetOrderDetailApplicationResponseDto getOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 - 모든 주문 조회 가능, 업체 담당자 - 해당 업체 주문 조회 가능

    Order order = orderRepository.findByIdAndDeletedAtIsNullFetchJoin(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    return GetOrderDetailApplicationResponseDto.from(order);
  }

  @Transactional
  @Override
  public CancelOrderApplicationResponseDto cancelOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 - 모든 주문 취소 가능, 업체 담당자 - 해당 업체 주문 취소 가능

    Order order = orderRepository.findByIdAndStatusAndDeletedAtIsNull(orderId, OrderStatus.CONFIRMED)
        .orElseThrow(() -> new CustomException(OrderErrorCode.UNABLE_CANCEL));

    // todo. 배송 취소 처리, 결제 취소 처리, 재고 상품 롤백
    // 1. 배송 취소 처리 (예를 들어, READY 상태면 가능, 논의 필요)
    // 2. 배송 취소 후 결제 취소 처리
    // 3. 결제 취소 후 재고 상품 롤백

    // 4. 주문 취소
    order.cancel();

    return CancelOrderApplicationResponseDto.of(order.getId(), order.getStatus());
  }

  @Transactional
  @Override
  public void deleteOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 삭제는 완료 혹은 취소 상태인 주문에만 가능
    // 마스터 - 모든 업체 주문 삭제 가능, 업체 담당자 - 해당 업체 주문 삭제 가능

    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    if (!order.isPossibleToDelete()) {
      throw new CustomException(OrderErrorCode.UNABLE_REMOVE);
    }

    LocalDateTime now = LocalDateTime.now();
    order.delete(now, userInfo.userId());
  }

  @Transactional
  @Override
  public InternalConfirmOrderApplicationResponseDto internalConfirmOrderById(UUID orderId) {

    // 결제 서비스가 존재한다는 가정하에 진행
    Order order = orderRepository.findByIdAndStatusAndDeletedAtIsNull(orderId, OrderStatus.ACCEPTED)
        .orElseThrow(() -> new CustomException(OrderErrorCode.UNABLE_CONFIRM));

    // 1. 주문 확정
    order.confirm();

    // todo. 배송 생성 요청
    // 2. 배송 생성 요청

    return InternalConfirmOrderApplicationResponseDto.of(order.getId(), order.getStatus());
  }

  @Transactional
  @Override
  public InternalUpdateOrderStatusApplicationResponseDto internalUpdateOrderStatusById(
      UUID orderId, String status) {

    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    order.updateStatus(OrderStatus.parse(status));
    return InternalUpdateOrderStatusApplicationResponseDto.of(
        order.getId(), order.getStatus().toString());
  }
}
