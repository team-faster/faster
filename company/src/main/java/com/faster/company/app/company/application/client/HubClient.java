package com.faster.company.app.company.application.client;

import com.faster.company.app.company.application.dto.response.GetHubsApplicationResponseDto;
import java.util.UUID;

public interface HubClient {

  GetHubsApplicationResponseDto getHubById(UUID hubId);
}
