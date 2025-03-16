package com.faster.order.app.order.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.domain.entity.Order;
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
  public OrderDetailApplicationResponseDto getOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 - 모든 주문 조회 가능, 업체 담당자 - 해당 업체 주문 조회 가능

    Order order = orderRepository.findByIdAndDeletedAtIsNullFetchJoin(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    return OrderDetailApplicationResponseDto.from(order);
  }

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

  @Override
  public PageResponse<SearchOrderApplicationResponseDto> getOrdersByCondition(CurrentUserInfoDto userInfo,
      Pageable pageable, SearchOrderConditionDto condition) {

    // todo. 유저정보에 따라 주문 정보 접근 권한 체크
    // 마스터 모든 주문 조회 가능
    // 업체 담당자 - 해당 업체 주문만 조회 가능
    UUID companyId = userInfo.role() == UserRole.ROLE_MASTER ? null : UUID.randomUUID();
    Page<SearchOrderApplicationResponseDto> pageList = orderRepository.getOrdersByConditionAndCompanyId(
        pageable, condition, companyId, userInfo.role())
        .map(SearchOrderApplicationResponseDto::from);
    return PageResponse.from(pageList);
  }
}
