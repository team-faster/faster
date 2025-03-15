package com.faster.hub.app.hub.application.usecase;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;
import com.faster.hub.app.hub.application.dto.GetHubResponseApplicationResponseDto;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService{
  private final HubRepository hubRepository;

  @Override
  public CreateHubResponseApplicationResponseDto createHub(CreateHubApplicationRequestDto dto) {
    return CreateHubResponseApplicationResponseDto.from(
        hubRepository.save(dto.toEntity()));
  }

  @Override
  @Transactional(readOnly = true)
  public GetHubResponseApplicationResponseDto getHub(UUID hubId) {
    return GetHubResponseApplicationResponseDto.from(
        hubRepository.findById(hubId).orElseThrow(
            () -> CustomException.from(HubErrorCode.NOT_FOUND)
        )
    );
  }
}
