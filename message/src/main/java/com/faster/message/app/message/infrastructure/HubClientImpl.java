package com.faster.message.app.message.infrastructure;

import com.faster.message.app.message.application.client.HubClient;
import com.faster.message.app.message.application.dto.response.AGetHubResponseDto;
import com.faster.message.app.message.infrastructure.fegin.HubFeignClient;
import com.faster.message.app.message.infrastructure.fegin.dto.response.IGetHubResponseDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

  private final HubFeignClient hubFeignClient;

  @Override
  public AGetHubResponseDto getOrderByOrderId(List<UUID> hubIds) {

    IGetHubResponseDto data = hubFeignClient.getHubs(hubIds).getBody().data();

    if (data == null) {
      return null; // TODO: 에러 처리 추후에 진행
    }

    List<AGetHubResponseDto.HubInfo> hubInfoList = data.hubInfos().stream()
        .map(hubInfo -> AGetHubResponseDto.HubInfo.builder()
            .hubManagerUserId(hubInfo.managerId())
            .name(hubInfo.name())
            .address(hubInfo.address())
            .build())
        .collect(Collectors.toList());

    return AGetHubResponseDto.builder()
        .hubInfos(hubInfoList)
        .build();
  }
}
