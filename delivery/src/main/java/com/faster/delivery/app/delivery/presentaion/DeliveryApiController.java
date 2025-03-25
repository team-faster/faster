package com.faster.delivery.app.delivery.presentaion;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.delivery.app.delivery.application.dto.DeliveryDetailDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryGetElementDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryRouteUpdateDto;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.dto.DeliveryUpdateDto;
import com.faster.delivery.app.delivery.application.usecase.DeliveryService;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliveryGetDetailResponseDto;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliveryRouteUpdateRequestDto;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliverySaveApiRequestDto;
import com.faster.delivery.app.delivery.presentaion.dto.api.DeliveryUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배송", description = "배송 생성 및 수정")
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
@RestController
public class DeliveryApiController {

  private final DeliveryService deliveryService;

  @Operation(summary = "배송 저장", description = "배송 저장 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, UUID>>> saveDelivery(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody DeliverySaveApiRequestDto deliverySaveRequestDto) {

    DeliverySaveDto saveDto = deliverySaveRequestDto.toSaveDto();
    UUID uuid = deliveryService.saveDelivery(saveDto);
    Map<String, UUID> data = Map.of("deliveryId", uuid);

    return ResponseEntity
        .status(HttpStatus.CREATED.value())
        .body(ApiResponse.of(HttpStatus.CREATED, "Success", data));
  }

  @Operation(summary = "배송 조회", description = "배송 조회 API 입니다.")
  @AuthCheck
  @GetMapping("/{deliveryId}")
  public ResponseEntity<ApiResponse<DeliveryGetDetailResponseDto>> getDeliveryDetail(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryId") UUID deliveryId) {

    // 상세 조회
    DeliveryDetailDto deliveryDetail = deliveryService.getDeliveryDetail(deliveryId, userInfo);
    DeliveryGetDetailResponseDto data = DeliveryGetDetailResponseDto.from(deliveryDetail);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", data));
  }

  @Operation(summary = "모든 배송 조회", description = "모든 배송 조회 API 입니다.")
  @AuthCheck
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<DeliveryGetElementDto>>> getDeliveryList(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PageableDefault
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "updatedAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(value = "search", required = false) String search
  ) {

    PageResponse<DeliveryGetElementDto> deliveries =
        deliveryService.getDeliveryList(pageable, search, userInfo);
    return ResponseEntity.ok()
        .body(ApiResponse.of(HttpStatus.OK, "Success", deliveries));
  }

  @Operation(summary = "배송 상태 수정", description = "배송 상태 수정 API 입니다.")
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

  @Operation(summary = "배송 삭제", description = "배송 삭제 API 입니다.")
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

  @Operation(summary = "배송 경로 수정", description = "배송 경로 수정 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_DELIVERY, UserRole.ROLE_MASTER})
  @PatchMapping("/{deliveryId}/routes/{deliveryRouteId}")
  public ResponseEntity<ApiResponse<Map<String, UUID>>> updateDeliveryRoute(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryId") UUID deliveryId,
      @PathVariable("deliveryRouteId") UUID deliveryRouteId,
      @RequestBody DeliveryRouteUpdateRequestDto requestDto
  ) {
    DeliveryRouteUpdateDto deliveryRouteUpdateDto = requestDto.toDeliveryRouteUpdateDto();

    UUID updateDeliveryId = deliveryService.updateDeliverRoute(deliveryId, deliveryRouteId,
        deliveryRouteUpdateDto, userInfo);

    // 업데이트 성공 시 상세 조회를 위해 delivery Id 를 반환
    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryId", updateDeliveryId)));
  }
}
