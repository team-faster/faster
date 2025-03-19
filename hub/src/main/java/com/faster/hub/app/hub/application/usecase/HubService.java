package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.usecase.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.UpdateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubRoutesApplicationResponseDto;
import java.util.List;
import java.util.UUID;
import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubApplicationResponseDto;

public interface HubService {

  SaveHubApplicationResponseDto saveHub(SaveHubApplicationRequestDto from);

  GetHubApplicationResponseDto getHub(UUID hubId);

  GetPathsApplicationResponseDto getPaths(GetPathApplicationRequestDto dto);

  UpdateHubApplicationResponseDto updateHub(UpdateHubApplicationRequestDto dto);

  UpdateHubRoutesApplicationResponseDto updateHubRoutes();

  void deleteHub(DeleteHubApplicationRequestDto dto);

  GetHubsApplicationResponseDto getHubs(List<UUID> hubIds);
}
