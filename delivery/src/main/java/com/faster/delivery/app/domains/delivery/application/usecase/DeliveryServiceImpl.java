package com.faster.delivery.app.domains.delivery.application.usecase;

import com.common.response.ApiResponse;
import com.faster.delivery.app.domains.delivery.application.dto.DeliverySaveDto;
import com.faster.delivery.app.domains.delivery.domain.entity.Delivery;
import com.faster.delivery.app.domains.delivery.domain.entity.Delivery.Status;
import com.faster.delivery.app.domains.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.domains.delivery.domain.repository.DeliveryRepository;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.CompanyClient;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.HubClient;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.company.CompanyGetResponseDto;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.hub.HubPathRequestDto;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.hub.HubPathResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final CompanyClient companyClient;
  private final HubClient hubClient;

  @Transactional
  public UUID saveDelivery(Long userId, DeliverySaveDto deliverySaveDto) {

    // 수취 업체 정보 조회
    ApiResponse<CompanyGetResponseDto> companyResponse = companyClient.getCompanyData(
        deliverySaveDto.receiveCompanyId()
    );
    CompanyGetResponseDto companyData = companyResponse.data();

    // 허브 경로 조회
    ApiResponse<HubPathResponseDto> hubRouteResponse = hubClient.getHubRouteData(
        new HubPathRequestDto(deliverySaveDto.sourceHubId(), deliverySaveDto.destinationHubId())
    );

    // 경로 정보 구성
    HubPathResponseDto hubRouteData = hubRouteResponse.data();
    List<DeliveryRoute> deliveryRouteList = HubPathResponseDto.to(hubRouteData);

    // 배송 정보 구성
    Delivery delivery = Delivery.builder()
        .orderId(deliverySaveDto.orderId())
        .sourceHubId(deliverySaveDto.sourceHubId())
        .destinationHubId(deliverySaveDto.destinationHubId())
        .receiptCompanyId(deliverySaveDto.receiveCompanyId())
        .receiptCompanyAddress(companyData.address())
        .recipientName(companyData.companyManagerName())
        .recipientSlackId(companyData.companyManagerSlackId())
        .status(Status.INPROGRESS)
        .build();
    delivery.addDeliveryRouteList(deliveryRouteList);

    // save
    Delivery savedDelivery = deliveryRepository.save(delivery);
    return savedDelivery.getId();
  }
}
