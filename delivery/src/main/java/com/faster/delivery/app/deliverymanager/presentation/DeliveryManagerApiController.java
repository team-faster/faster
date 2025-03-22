package com.faster.delivery.app.deliverymanager.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerDetailDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerElementDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerUpdateDto;
import com.faster.delivery.app.deliverymanager.application.facade.DeliveryManagerFacade;
import com.faster.delivery.app.deliverymanager.application.usecase.DeliveryManagerService;
import com.faster.delivery.app.deliverymanager.presentation.dto.api.DeliveryManagerGetDetailResponseDto;
import com.faster.delivery.app.deliverymanager.presentation.dto.api.DeliveryManagerSaveRequestDto;
import com.faster.delivery.app.deliverymanager.presentation.dto.api.DeliveryManagerUpdateRequestDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
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

@RequiredArgsConstructor
@RequestMapping("/api/delivery-managers")
@RestController
public class DeliveryManagerApiController {

  private final DeliveryManagerService deliveryManagerService;
  private final DeliveryManagerFacade deliveryManagerFacade;

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Long>>> saveDeliveryManager(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody DeliveryManagerSaveRequestDto requestDto) {
    DeliveryManagerSaveDto saveDto = requestDto.toSaveDto();
    Long deliveryManagerId = deliveryManagerFacade.saveDeliveryManager(saveDto);
    Map<String, Long> data = Map.of("deliveryManagerId", deliveryManagerId);

    return ResponseEntity
        .status(HttpStatus.CREATED.value())
        .body(ApiResponse.of(HttpStatus.CREATED, "Success", data));
  }

  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_DELIVERY, UserRole.ROLE_MASTER})
  @GetMapping("/{deliveryManagerId}")
  public ResponseEntity<ApiResponse<DeliveryManagerGetDetailResponseDto>> getDeliveryManagerDetails(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryManagerId") Long deliveryManagerId
  ) {

    DeliveryManagerDetailDto deliveryManagerDetail = deliveryManagerService.getDeliveryManagerDetail(
        userInfo, deliveryManagerId);

    DeliveryManagerGetDetailResponseDto data = DeliveryManagerGetDetailResponseDto.from(
        deliveryManagerDetail);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", data));
  }

  @AuthCheck
  @GetMapping()
  public ResponseEntity<ApiResponse<PageResponse<DeliveryManagerElementDto>>> getDeliveryManagerList(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PageableDefault
      @SortDefaults({
          @SortDefault(sort = "createdAt", direction = Direction.DESC),
          @SortDefault(sort = "updatedAt", direction = Direction.DESC)
      }) Pageable pageable,
      @RequestParam(value = "search", required = false) String search
  ) {
    PageResponse<DeliveryManagerElementDto> deliveryManagerList = deliveryManagerService
        .getDeliveryManagerList(pageable, search, userInfo);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", deliveryManagerList));
  }

  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_MASTER})
  @PatchMapping("/{deliveryManagerId}")
  public ResponseEntity<ApiResponse<Map<String, Long>>> updateDeliveryManager(
      @RequestBody DeliveryManagerUpdateRequestDto requestDto,
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryManagerId") Long deliveryManagerId
  ) {

    DeliveryManagerUpdateDto updateDto = requestDto.toUpdateDto();
    Long updatedDeliveryManagerId = deliveryManagerService.updateDeliveryManager(
        deliveryManagerId, updateDto, userInfo);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryManagerId", updatedDeliveryManagerId)));
  }

  @AuthCheck(roles = {UserRole.ROLE_HUB, UserRole.ROLE_MASTER})
  @DeleteMapping("/{deliveryManagerId}")
  public ResponseEntity<ApiResponse<Map<String, Long>>> deleteDeliveryManager(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable("deliveryManagerId") Long deliveryManagerId) {

    Long deletedDeliveryManagerId = deliveryManagerService.deleteDeliveryManager(deliveryManagerId, userInfo);

    return ResponseEntity
        .status(HttpStatus.OK.value())
        .body(ApiResponse.of(HttpStatus.OK, "Success", Map.of("deliveryManagerId", deletedDeliveryManagerId)));
  }
}
