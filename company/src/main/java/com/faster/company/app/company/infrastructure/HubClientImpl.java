package com.faster.company.app.company.infrastructure;

import com.faster.company.app.company.application.client.HubClient;
import com.faster.company.app.company.application.dto.response.GetHubsApplicationResponseDto;
import com.faster.company.app.company.infrastructure.feign.HubFeignClient;
import com.faster.company.app.company.infrastructure.feign.dto.response.GetHubsResponseDto;
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
