package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.GetHubApplicationResponseDto;
import java.util.UUID;

public interface HubService {

  CreateHubResponseApplicationResponseDto createHub(CreateHubApplicationRequestDto from);

  GetHubApplicationResponseDto getHub(UUID hubId);
}
