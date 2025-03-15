package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;

public interface HubService {

  CreateHubResponseApplicationResponseDto createHub(CreateHubApplicationRequestDto from);
}
