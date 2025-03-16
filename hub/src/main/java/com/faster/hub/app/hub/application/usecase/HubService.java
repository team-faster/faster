package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.UpdateHubApplicationRequestDto;
import java.util.UUID;
import com.faster.hub.app.hub.application.dto.UpdateHubApplicationResponseDto;

public interface HubService {

  SaveHubApplicationResponseDto saveHub(SaveHubApplicationRequestDto from);

  GetHubApplicationResponseDto getHub(UUID hubId);

  UpdateHubApplicationResponseDto updateHub(UpdateHubApplicationRequestDto dto);

  void deleteHub(DeleteHubApplicationRequestDto dto);
}
