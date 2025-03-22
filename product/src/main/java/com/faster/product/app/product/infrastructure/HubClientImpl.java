package com.faster.product.app.product.infrastructure;

import com.faster.product.app.product.application.client.HubClient;
import com.faster.product.app.product.application.dto.response.GetHubsApplicationResponseDto;
import com.faster.product.app.product.infrastructure.feign.HubFeignClient;
import com.faster.product.app.product.infrastructure.feign.dto.response.GetHubsResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {
  private final HubFeignClient hubFeignClient;

  @Override
  public GetHubsApplicationResponseDto getHubById(UUID hubId) {

    GetHubsResponseDto hubDto = hubFeignClient.getHubById(hubId).getBody().data();
    return hubDto.toApplicationDto();
  }
}
