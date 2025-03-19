package com.faster.delivery.app.delivery.presentaion;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import com.faster.delivery.app.delivery.application.usecase.DeliveryService;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliverySaveRequestDto;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliveryUpdateRequestDto;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
@RestController
public class DeliveryApiController {

  private final DeliveryService deliveryService;

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, UUID>>> saveDelivery(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody DeliverySaveRequestDto deliverySaveRequestDto) {

    DeliverySaveDto saveDto = deliverySaveRequestDto.toSaveDto();
    UUID uuid = deliveryService.saveDelivery(saveDto);
    Map<String, UUID> data = Map.of("deliveryId", uuid);

    return ResponseEntity
        .status(HttpStatus.CREATED.value())
        .body(ApiResponse.of(HttpStatus.CREATED, "Success", data));
  }

  @AuthCheck
  @GetMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<Map<String, UUID>>> getDeliveryDetail(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryId") UUID deliveryId) {

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", null));
  }

  @AuthCheck(roles = {UserRole.ROLE_DELIVERY, UserRole.ROLE_HUB, UserRole.ROLE_MASTER})
  @PatchMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<Map<String, UUID>>> updateDeliveryStatus(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryId") UUID deliveryId,
      @RequestBody DeliveryUpdateRequestDto deliveryUpdateRequestDto ) {

    DeliveryUpdateDto deliveryUpdateDto = deliveryUpdateRequestDto.toDeliveryUpdateDto();

    UUID updateDeliveryId = deliveryService.updateDeliveryStatus(userInfo, deliveryId, deliveryUpdateDto);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryId", updateDeliveryId)));
  }

  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_MASTER})
  @DeleteMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<Map<String, UUID>>> deleteDelivery(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryId") UUID deliveryId
  ) {

    UUID deleteDeliveryId = deliveryService.deleteDelivery(deliveryId, userInfo);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryId", deleteDeliveryId)));
  }
}
