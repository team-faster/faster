package com.faster.delivery.app.deliverymanager.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.usecase.DeliveryManagerService;
import com.faster.delivery.app.deliverymanager.presentation.dto.DeliveryManagerSaveRequestDto;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/delivery-managers")
@RestController
public class DeliveryManagerApiController {

  private final DeliveryManagerService deliveryManagerService;

  @AuthCheck(roles = {UserRole.ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, String>>> saveDeliveryManager(@RequestBody DeliveryManagerSaveRequestDto requestDto) {
    DeliveryManagerSaveDto saveDto = requestDto.toSaveDto();
    UUID deliveryManagerId = deliveryManagerService.saveDeliveryManager(saveDto);
    Map<String, String> data = Map.of("deliveryManagerId", deliveryManagerId.toString());

    return ResponseEntity
        .status(HttpStatus.CREATED.value())
        .body(ApiResponse.of(HttpStatus.CREATED, "Success", data));
  }
}
