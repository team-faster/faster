package com.faster.delivery.app.delivery.presentaion;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import com.faster.delivery.app.delivery.application.usecase.DeliveryService;
import com.faster.delivery.app.delivery.presentaion.dto.internal.DeliverySaveInternalRequestDto;
import com.faster.delivery.app.delivery.presentaion.dto.internal.DeliveryUpdateInternalRequestDto;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/deliveries")
@RestController
public class DeliveryInternalController {

  private final DeliveryService deliveryService;

  @AuthCheck(roles = {UserRole.ROLE_COMPANY, UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, UUID>>> saveDelivery(
      @RequestBody DeliverySaveInternalRequestDto requestDto) {

    UUID savedDeliveryId = deliveryService.saveDeliveryInternal(requestDto.toApplicationDto());

    return ResponseEntity
        .status(HttpStatus.CREATED.value())
        .body(ApiResponse.of(
            HttpStatus.CREATED, "Success", Map.of("deliveryId", savedDeliveryId)));
  }

  @AuthCheck(roles = {UserRole.ROLE_COMPANY, UserRole.ROLE_HUB, UserRole.ROLE_MASTER})
  @PatchMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<Map<String, UUID>>> updateDelivery(
      @RequestBody DeliveryUpdateInternalRequestDto requestDto,
      @PathVariable UUID deliveryId,
      @CurrentUserInfo CurrentUserInfoDto userInfo) {

    DeliveryUpdateDto updateDto = requestDto.toDeliveryUpdateDto();

    UUID updatedDeliveryId = deliveryService.updateDeliveryStatusInternal(deliveryId, updateDto, userInfo);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryId", updatedDeliveryId)));
  }
}
