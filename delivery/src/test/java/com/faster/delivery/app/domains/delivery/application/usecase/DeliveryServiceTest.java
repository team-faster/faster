package com.faster.delivery.app.domains.delivery.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.delivery.application.usecase.DeliveryService;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.infrastructure.feign.CompanyClient;
import com.faster.delivery.app.delivery.infrastructure.feign.HubClient;
import com.faster.delivery.app.delivery.infrastructure.feign.dto.company.CompanyGetResponseDto;
import com.faster.delivery.app.delivery.infrastructure.feign.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.delivery.infrastructure.feign.dto.hub.HubPathResponseDto;
import com.faster.delivery.app.delivery.infrastructure.feign.dto.hub.HubPathResponseDto.RouteDto;
import com.faster.delivery.app.delivery.infrastructure.jpa.DeliveryJpaRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Slf4j
@ActiveProfiles({"test"})
@SpringBootTest
class DeliveryServiceTest {

  @MockitoBean
  CompanyClient companyClient;

  @MockitoBean
  HubClient hubClient;

  @Autowired
  DeliveryJpaRepository deliveryJpaRepository;

  @Autowired
  DeliveryService deliveryService;


  @Test
  @Transactional
  void should_save_delivery() {

    UUID companyId = UUID.randomUUID();

    // given
    // companyClient : 업체 정보 조회
    Mockito.when(companyClient.getCompanyData(companyId)).thenReturn(
        ApiResponse.of(
            HttpStatus.OK,
            "조회성공",
            CompanyGetResponseDto.builder()
                .id(companyId)
                .companyManagerId(123L)
                .companyManagerName("매니저이름")
                .companyManagerSlackId("slackId123")
                .name("JJW컴퍼니")
                .contact("JJW@naver.com")
                .address("사랑시 고백구 행복동")
                .hubId(UUID.randomUUID())
                .type("공급업체")
                .build()
        )
    );

    // hubClient : 허브 경로 정보 조회
    // 요청 정보 구성
    UUID sourceHubId = UUID.randomUUID();
    UUID destinationHubId = UUID.randomUUID();
    HubPathRequestDto hubReqDto = HubPathRequestDto.builder()
        .sourceHubId(sourceHubId)
        .destinationHubId(destinationHubId)
        .build();

    // 응답 정보 구성
    ArrayList<UUID> hubSequence = new ArrayList<>();
    hubSequence.add(sourceHubId);
    for (int i = 0; i < 4; i++) {
      hubSequence.add(UUID.randomUUID());
    }
    hubSequence.add(destinationHubId);

    ArrayList<RouteDto> routeDtoList = mkRouteRespDto(
        hubSequence
    );

    Mockito.when(hubClient.getHubRouteData(hubReqDto))
        .thenReturn(ApiResponse.of(
            HttpStatus.OK,
            "조회성공",
            HubPathResponseDto.builder()
                .contents(
                    routeDtoList
                )
                .build()
        )
    );

    // save 요청 Dto 구성
    UUID orderId = UUID.randomUUID();
    DeliverySaveDto saveDto = DeliverySaveDto.builder()
        .orderId(orderId)
        .sourceHubId(sourceHubId)
        .destinationHubId(destinationHubId)
        .receiveCompanyId(companyId)
        .build();

    // when
    UUID uuid = deliveryService.saveDelivery(123L, saveDto);

    // then
    Delivery delivery = deliveryJpaRepository.findById(uuid).get();

    List<DeliveryRoute> deliveryRouteList = delivery.getDeliveryRouteList();

    assertEquals(orderId, delivery.getOrderId());
    assertEquals(5, delivery.getDeliveryRouteList().size());
  }

  private static ArrayList<RouteDto> mkRouteRespDto(ArrayList<UUID> hubSequence) {
    int cnt = 1;
    UUID beforeHub = null;
    ArrayList<RouteDto> routeDtoList = new ArrayList<>();
    for (UUID hubId : hubSequence) {
      if (beforeHub == null) {
        beforeHub = hubId;
        continue;
      }

      RouteDto routeDto = RouteDto.builder()
          .sequence(cnt)
          .sourceHubId(beforeHub)
          .destinationHubId(hubId)
          .expectedTimeMin(123L)
          .expectedDistanceM(123L)
          .build();

      routeDtoList.add(routeDto);
      beforeHub = hubId;
      cnt++;
    }
    return routeDtoList;
  }
}