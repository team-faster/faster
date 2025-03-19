package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.domain.entity.Hub;
import java.util.List;
import java.util.UUID;

public interface PathFinder {

  GetPathsApplicationResponseDto findShortestPath(UUID uuid, UUID uuid1, List<Hub> all);
}
