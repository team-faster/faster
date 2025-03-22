package com.faster.product.app.product.application.client;

import com.faster.product.app.product.application.dto.response.GetHubsApplicationResponseDto;
import java.util.UUID;

public interface HubClient {

  GetHubsApplicationResponseDto getHubById(UUID hubId);
}
