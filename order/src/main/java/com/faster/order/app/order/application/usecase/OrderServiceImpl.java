package com.faster.order.app.order.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.application.CompanyClient;
import com.faster.order.app.order.application.ProductClient;
import com.faster.order.app.order.application.dto.request.GetProductsApplicationResponseDto;
import com.faster.order.app.order.application.dto.request.GetProductsApplicationResponseDto.GetProductApplicationResponseDto;
import com.faster.order.app.order.application.dto.request.SaveOrderApplicationRequestDto;
import com.faster.order.app.order.application.dto.request.SaveOrderApplicationRequestDto.SaveOrderItemApplicationRequestDto;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.application.dto.request.UpdateStocksApplicationRequestDto;
import com.faster.order.app.order.application.dto.response.CancelOrderApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.GetOrderDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.InternalConfirmOrderApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.InternalUpdateOrderStatusApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.repository.OrderRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
  private final ProductClient productClient;
  private final CompanyClient companyClient;

  @Override
  public PageResponse<SearchOrderApplicationResponseDto> getOrdersByCondition(CurrentUserInfoDto userInfo,
      Pageable pageable, SearchOrderConditionDto condition) {

    // 1. 마스터 - 모든 주문 조회 가능
    // 2. 업체 담당자 - 해당 업체 주문만 조회 가능
    GetCompanyApplicationResponseDto company = getCompanyDto(userInfo);
    UUID companyId = company == null ? null : company.id();

    Page<SearchOrderApplicationResponseDto> pageList = orderRepository.getOrdersByConditionAndCompanyId(
            pageable, condition.toCriteria(), companyId, userInfo.role())
        .map(SearchOrderApplicationResponseDto::from);
    return PageResponse.from(pageList);
  }

  @Override
  public GetOrderDetailApplicationResponseDto getOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    Order order = orderRepository.findByIdAndDeletedAtIsNullFetchJoin(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    // 권한 검증
    // 1. 마스터 - 모든 주문 조회 가능
    // 2. 업체 담당자 - 해당 업체 주문 조회 가능
    this.checkIfValidAccessToModify(userInfo, order.getReceivingCompanyId(), OrderErrorCode.FORBIDDEN);

    return GetOrderDetailApplicationResponseDto.from(order);
  }

  @Transactional
  @Override
  public CancelOrderApplicationResponseDto cancelOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    Order order = orderRepository.findByIdAndStatusAndDeletedAtIsNullFetchJoin(orderId, OrderStatus.CONFIRMED)
        .orElseThrow(() -> new CustomException(OrderErrorCode.UNABLE_CANCEL));

    // 권한 검증
    // 1. 마스터 - 모든 주문 취소 가능
    // 2. 업체 담당자 - 해당 업체 주문 취소 가능
    this.checkIfValidAccessToModify(userInfo, order.getReceivingCompanyId(), OrderErrorCode.FORBIDDEN);

    // todo. 배송 취소 처리, 결제 취소 처리
    // 1. 배송 취소 처리 (예를 들어, READY 상태면 가능, 논의 필요)
    // 2. 배송 취소 후 결제 취소 처리

    // 3. 결제 취소 후 재고 상품 롤백
    UpdateStocksApplicationResponseDto updateStocksApplicationResponseDto = productClient.updateStocks(
        UpdateStocksApplicationRequestDto.from(order.getOrderItems()));

    // 4. 주문 취소
    order.cancel();
    return CancelOrderApplicationResponseDto.of(order.getId(), order.getStatus());
  }

  @Transactional
  @Override
  public void deleteOrderById(CurrentUserInfoDto userInfo, UUID orderId) {

    // 삭제는 완료 혹은 취소 상태인 주문에만 가능
    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_ORDER_ID));

    // 권한 검증
    // 1. 마스터 - 모든 업체 주문 삭제 가능
    // 2. 업체 담당자 - 해당 업체 주문 삭제 가능
    this.checkIfValidAccessToModify(userInfo, order.getReceivingCompanyId(), OrderErrorCode.FORBIDDEN);

    if (!order.isPossibleToDelete()) {
      throw new CustomException(OrderErrorCode.UNABLE_REMOVE);
    }
    order.softDelete(userInfo.userId());
  }

  @Transactional
  @Override
  public UUID saveOrder(CurrentUserInfoDto userInfo,
      SaveOrderApplicationRequestDto applicationRequestDto) {

    // 권한 검증
    // 1. 마스터 - 모든 주문 생성 가능
    // 2. 업체 담당자 - 해당 업체 주문 생성 가능
    this.checkIfValidAccessToModify(
        userInfo, applicationRequestDto.receivingCompanyId(), OrderErrorCode.FORBIDDEN_SAVE);

    Map<UUID, Integer> productStocksMap = applicationRequestDto.toProductStocksMap();

    // 상품 정보 조회하여 검증 로직 수행
    GetProductsApplicationResponseDto productsDto = productClient.getProducts(productStocksMap.keySet());
    this.validateOrderRequest(applicationRequestDto, productStocksMap, productsDto);

    // 재고 차감 요청 수행
    UpdateStocksApplicationResponseDto updateStocksDto =
        productClient.updateStocks(UpdateStocksApplicationRequestDto.from(productStocksMap));

    Order order = applicationRequestDto.toEntity();
    orderRepository.save(order);
    return order.getId();
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

  private void validateOrderRequest(
      SaveOrderApplicationRequestDto SaveOrderDto, Map<UUID, Integer> productStocksMap,
      GetProductsApplicationResponseDto productsDto) {

    Set<UUID> hubIds = new HashSet<>();
    for (SaveOrderItemApplicationRequestDto orderItem : SaveOrderDto.orderItems()) {
      GetProductApplicationResponseDto productDto = productsDto.products()
          .get(orderItem.productId());

      if (productDto.quantity() < productStocksMap.get(orderItem.productId())) {
        throw new CustomException(OrderErrorCode.NOT_ENOUGH_STOCK);
      }
      if (!productDto.name().equals(orderItem.name())) {
        throw new CustomException(OrderErrorCode.NOT_VALID_PRD_NAME);
      }
      if (productDto.price().compareTo(orderItem.price()) != 0) {
        throw new CustomException(OrderErrorCode.NOT_VALID_PRD_PRICE);
      }
      hubIds.add(productDto.hubId());
    }

    // 하나의 주문은 하나의 허브만 가질 수 있다.
    if (hubIds.size() > 1) {
      throw new CustomException(OrderErrorCode.MULTIPLE_HUB);
    }
  }

  private void checkIfValidAccessToModify(CurrentUserInfoDto userInfo, UUID receivingCompanyId, OrderErrorCode code) {

    GetCompanyApplicationResponseDto company = getCompanyDto(userInfo);
    if (company != null && company.id() != receivingCompanyId) {
      throw new CustomException(code);
    }
  }

  private GetCompanyApplicationResponseDto getCompanyDto(CurrentUserInfoDto userInfo) {
    GetCompanyApplicationResponseDto company = null;
    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      company = companyClient.getCompanyByCompanyManagerId(userInfo.userId());
    }
    return company;
  }
}
