package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.UpdateHubApplicationRequestDto;
import java.util.UUID;
import com.faster.hub.app.hub.application.dto.UpdateHubApplicationResponseDto;

public interface HubService {

  CreateHubResponseApplicationResponseDto createHub(CreateHubApplicationRequestDto from);

  GetHubApplicationResponseDto getHub(UUID hubId);

  UpdateHubApplicationResponseDto updateHub(UpdateHubApplicationRequestDto dto);
}
