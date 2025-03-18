package com.faster.hub.app.hub.application.usecase;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.request.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.response.GetPathsApplicationResponseDto.Path;
import com.faster.hub.app.hub.application.dto.response.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.response.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.request.UpdateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.response.UpdateHubApplicationResponseDto;
import com.faster.hub.app.hub.domain.entity.Hub;
import com.faster.hub.app.hub.domain.entity.HubRoute;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;

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
