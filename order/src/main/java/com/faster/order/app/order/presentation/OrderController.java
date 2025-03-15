package com.faster.order.app.order.presentation;

import com.common.response.ApiResponse;
import com.faster.order.app.order.application.usecase.OrderService;
import com.faster.order.app.order.presentation.dto.response.OrderDetailResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {
  private final OrderService orderService;

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderDetailResponseDto>> getOrderById(
      @PathVariable UUID orderId) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "주문이 성공적으로 조회되었습니다.",
            HttpStatus.OK.value(),
            OrderDetailResponseDto.from(orderService.getOrderById(orderId))));
  }
}
