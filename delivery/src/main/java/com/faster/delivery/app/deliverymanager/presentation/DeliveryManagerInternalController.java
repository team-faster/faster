package com.faster.delivery.app.deliverymanager.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.usecase.DeliveryManagerService;
import com.faster.delivery.app.deliverymanager.presentation.dto.AssignDeliveryManagerRequestDto;
import com.faster.delivery.app.deliverymanager.presentation.dto.internal.AssignDeliveryManagerResponse;
import com.faster.delivery.app.deliverymanager.presentation.dto.internal.DeliveryManagerGetDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배송 담당자(Internal)", description = "배송 담당자 생성 및 수정")
@RequiredArgsConstructor
@RequestMapping("/internal/delivery-managers")
@RestController
public class DeliveryManagerInternalController {

  private final DeliveryManagerService deliveryManagerService;

  @Operation(summary = "배송 담당자 조회", description = "배송 담당자 조회 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_DELIVERY, UserRole.ROLE_MASTER})
  @GetMapping("/{deliveryManagerId}")
  public ResponseEntity<ApiResponse<DeliveryManagerGetDetailResponseDto>> getDeliveryManagerDetails(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryManagerId") Long deliveryManagerId
  ) {
    DeliveryManagerDetailDto deliveryManagerDetail = deliveryManagerService.getDeliveryManagerDetailInternal(
        userInfo, deliveryManagerId);

    DeliveryManagerGetDetailResponseDto data = DeliveryManagerGetDetailResponseDto.from(
        deliveryManagerDetail);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", data));
  }

  @Operation(summary = "배송 담당자 조회(회원 ID)", description = "배송 담당자 조회(회원 ID) API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_DELIVERY, UserRole.ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<DeliveryManagerGetDetailResponseDto>> getDeliveryManagerByUserId(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestParam(name="user-id") Long userId
  ) {
    DeliveryManagerDetailDto deliveryManagerDetail =
        deliveryManagerService.getDeliveryManagerByUserIdInternal(userInfo, userId);

    DeliveryManagerGetDetailResponseDto data = DeliveryManagerGetDetailResponseDto.from(
        deliveryManagerDetail);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", data));
  }

  @Operation(summary = "배송 담당자 배정", description = "배송 담당자 배정 API 입니다.")
  @PostMapping("/assign")
  public ResponseEntity<ApiResponse<AssignDeliveryManagerResponse>>
  assignCompanyDeliveryManger(@RequestBody AssignDeliveryManagerRequestDto request){
    return ResponseEntity.ok(ApiResponse.ok(
        AssignDeliveryManagerResponse.from(
            deliveryManagerService.assignDeliveryManger(request.toApplicationDto()))
    ));
  }
}
