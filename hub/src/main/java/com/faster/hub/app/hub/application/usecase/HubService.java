package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.request.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.response.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.response.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.request.UpdateHubApplicationRequestDto;
import java.util.UUID;
import com.faster.hub.app.hub.application.dto.response.UpdateHubApplicationResponseDto;

public interface HubService {

  SaveHubApplicationResponseDto saveHub(SaveHubApplicationRequestDto from);

  GetHubApplicationResponseDto getHub(UUID hubId);

  GetPathsApplicationResponseDto getPaths(GetPathApplicationRequestDto dto);

  UpdateHubApplicationResponseDto updateHub(UpdateHubApplicationRequestDto dto);

  void deleteHub(DeleteHubApplicationRequestDto dto);
}
