package com.faster.order.app.order.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.application.usecase.OrderService;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.presentation.dto.response.CancelOrderResponseDto;
import com.faster.order.app.order.presentation.dto.response.GetOrderDetailResponseDto;
import com.faster.order.app.order.presentation.dto.response.SearchOrderResponseDto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {
  private final OrderService orderService;

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<SearchOrderResponseDto>>> getOrders(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PageableDefault
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(name = "min-total-price", required = false)
      @Positive @Digits(integer = 15, fraction = 2) BigDecimal minTotalPrice,
      @RequestParam(name = "max-total-price", required = false)
      @Positive @Digits(integer = 15, fraction = 2) BigDecimal maxTotalPrice,
      @RequestParam(name = "supplier-company-name", required = false) String supplierCompanyName,
      @RequestParam(name = "receiving-company-name", required = false) String receivingCompanyName,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "address", required = false) String address,
      @RequestParam(name = "contact", required = false) String contact,
      @RequestParam(name = "status", required = false) OrderStatus status,
      @RequestParam(name = "is-deleted", required = false) Boolean isDeleted,
      @RequestParam(name = "start-created-at", required = false) LocalDateTime startCreatedAt,
      @RequestParam(name = "end-created-at", required = false) LocalDateTime endCreatedAt
  ) {

    PageResponse<SearchOrderApplicationResponseDto> pageResponse =
        orderService.getOrdersByCondition(userInfo, pageable, SearchOrderConditionDto.of(
            minTotalPrice, maxTotalPrice, supplierCompanyName, receivingCompanyName,
            name, address, contact, status, isDeleted, startCreatedAt, endCreatedAt));


    return ResponseEntity.ok()
        .body(ApiResponse.of(
            HttpStatus.OK,
            "주문이 성공적으로 조회되었습니다.",
            pageResponse.map(SearchOrderResponseDto::from)));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<GetOrderDetailResponseDto>> getOrderById(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID orderId) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문이 성공적으로 조회되었습니다.",
            HttpStatus.OK.value(),
            GetOrderDetailResponseDto.from(orderService.getOrderById(userInfo, orderId))));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse<CancelOrderResponseDto>> cancelOrderById(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID orderId) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문 취소가 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            CancelOrderResponseDto.from(orderService.cancelOrderById(userInfo, orderId))));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse<Void>> deleteOrderById(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID orderId) {

    orderService.deleteOrderById(userInfo, orderId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문이 성공적으로 삭제되었습니다.",
            HttpStatus.OK.value(),
            null));
  }
}
