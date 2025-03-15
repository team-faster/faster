package com.faster.hub.app.hub.application.usecase;

import com.faster.hub.app.hub.application.dto.CreateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.dto.CreateHubResponseApplicationResponseDto;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService{
  private final HubRepository hubRepository;

  @Override
  public CreateHubResponseApplicationResponseDto createHub(CreateHubApplicationRequestDto dto) {
    return CreateHubResponseApplicationResponseDto.from(
        hubRepository.save(dto.toEntity()));
  }
}
