package com.faster.order.app.order.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.order.app.order.application.usecase.OrderService;
import com.faster.order.app.order.presentation.dto.request.InternalUpdateOrderStatusRequestDto;
import com.faster.order.app.order.presentation.dto.response.InternalConfirmOrderResponseDto;
import com.faster.order.app.order.presentation.dto.response.InternalUpdateOrderStatusResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/orders")
@RestController
public class OrderInternalController {
  private final OrderService orderService;

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping("/{orderId}/confirm")
  public ResponseEntity<ApiResponse<InternalConfirmOrderResponseDto>> internalConfirmOrderById(
      @PathVariable UUID orderId) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문 확정이 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            InternalConfirmOrderResponseDto.from(orderService.internalConfirmOrderById(orderId))));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_HUB, UserRole.ROLE_DELIVERY, UserRole.ROLE_COMPANY})
  @PatchMapping("/{orderId}/status")
  public ResponseEntity<ApiResponse<InternalUpdateOrderStatusResponseDto>> internalUpdateOrderStatusById(
      @PathVariable UUID orderId, @RequestBody InternalUpdateOrderStatusRequestDto requestDto) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문 상태 변경이 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            InternalUpdateOrderStatusResponseDto.from(
                orderService.internalUpdateOrderStatusById(orderId, String.valueOf(requestDto.status())))));
  }
}
