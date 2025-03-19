package com.faster.hub.app.hub.application.usecase;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.usecase.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.UpdateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubApplicationResponseDto;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;
  private final PathFinder pathFinder;

  @Override
  public SaveHubApplicationResponseDto saveHub(SaveHubApplicationRequestDto dto) {
    return SaveHubApplicationResponseDto.from(
        hubRepository.save(dto.toEntity()));
  }

  @Override
  @Transactional(readOnly = true)
  public GetHubApplicationResponseDto getHub(UUID hubId) {
    return GetHubApplicationResponseDto.from(
        hubRepository.findById(hubId).orElseThrow(
            () -> CustomException.from(HubErrorCode.NOT_FOUND)
        )
    );
  }

  @Override
  public GetHubsApplicationResponseDto getHubs(List<UUID> hubIds) {
    return GetHubsApplicationResponseDto.from(
        hubRepository.findAllById(hubIds)
    );
  }

  @Override
  @Transactional(readOnly = true)
  public GetPathsApplicationResponseDto getPaths(GetPathApplicationRequestDto dto) {
    return pathFinder.findShortestPath(
        dto.sourceHubId(), dto.destinationHubId(), hubRepository.findAll());
  }

  @Override
  @Transactional
  public UpdateHubApplicationResponseDto updateHub(UpdateHubApplicationRequestDto dto) {
    return UpdateHubApplicationResponseDto.from(
        hubRepository.findById(dto.id()).orElseThrow(
            () -> CustomException.from(HubErrorCode.NOT_FOUND)
        ).update(dto.name(), dto.address(), dto.latitude(), dto.longitude())
    );
  }

  @Override
  @Transactional
  public void deleteHub(DeleteHubApplicationRequestDto dto) {
    hubRepository.findById(dto.hubId()).orElseThrow(
        () -> CustomException.from(HubErrorCode.NOT_FOUND)
    ).delete(dto.deletedAt(), dto.deleterId());
  }
}