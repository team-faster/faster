package com.faster.delivery.app.deliverymanager.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.usecase.DeliveryManagerService;
import com.faster.delivery.app.deliverymanager.presentation.dto.internal.DeliveryManagerGetDetailResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/delivery-managers")
@RestController
public class DeliveryManagerInternalController {

  private final DeliveryManagerService deliveryManagerService;

  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_DELIVERY, UserRole.ROLE_MASTER})
  @GetMapping("/{deliveryManagerId}")
  public ResponseEntity<ApiResponse<DeliveryManagerGetDetailResponseDto>> getDeliveryManagerDetails(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryManagerId") UUID deliveryManagerId
  ) {

    DeliveryManagerDetailDto deliveryManagerDetail = deliveryManagerService.getDeliveryManagerDetailInternal(
        userInfo, deliveryManagerId);

    DeliveryManagerGetDetailResponseDto data = DeliveryManagerGetDetailResponseDto.from(
        deliveryManagerDetail);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", data));
  }
}
