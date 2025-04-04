package com.faster.hub.app.hub.application.usecase;

import com.common.exception.CustomException;
import com.common.response.PageResponse;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.usecase.dto.request.DeleteHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.GetHubsApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.GetPathApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.SaveHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.request.SearchHubCondition;
import com.faster.hub.app.hub.application.usecase.dto.request.UpdateHubApplicationRequestDto;
import com.faster.hub.app.hub.application.usecase.dto.response.DirectionsApiApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationInternalResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetHubsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.SaveHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.UpdateHubRoutesApplicationResponseDto;
import com.faster.hub.app.hub.domain.entity.Hub;
import com.faster.hub.app.hub.domain.entity.HubRoute;
import com.faster.hub.app.hub.domain.repository.HubRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;
  private final PathFinder pathFinder;
  private final DirectionsApiClient directionsApiClient;

  @Override
  public SaveHubApplicationResponseDto saveHub(SaveHubApplicationRequestDto dto) {
    return SaveHubApplicationResponseDto.from(
        hubRepository.save(dto.toEntity()));
  }

  @Override
  @Cacheable(
      cacheNames = "getHub",
      key = "'hubs:' + #hubId",
      cacheManager = "hubCacheManager"
  )
  @Transactional(readOnly = true)
  public GetHubApplicationResponseDto getHub(UUID hubId) {
    return GetHubApplicationResponseDto.from(
        hubRepository.findById(hubId).orElseThrow(
            () -> CustomException.from(HubErrorCode.NOT_FOUND)
        )
    );
  }

  @Override
  @Cacheable(
      cacheNames = "getHubs",
      key = "'hubs:' + #dto.pageable.pageNumber + ':' + #dto.pageable.pageSize + " +
          "':searchText:' + #dto.searchText + " +
          "':nameSearchText:' + #dto.nameSearchText + " +
          "':addressSearchText:' + #dto.addressSearchText",
      cacheManager = "hubCacheManager"
  )
  public PageResponse<GetHubsApplicationResponseDto> getHubs(GetHubsApplicationRequestDto dto) {
    return PageResponse.from(hubRepository.searchHubsByCondition(
        dto.pageable(),
        SearchHubCondition.of(dto.searchText(), dto.nameSearchText(), dto.addressSearchText())
    ).map(GetHubsApplicationResponseDto::from));
  }

  @Override
  public GetHubsApplicationInternalResponseDto getHubsInternal(List<UUID> hubIds, Long hubManagerId) {
    // todo: 동적 쿼리로 개선 필요
    if(hubManagerId != null){
      return GetHubsApplicationInternalResponseDto.from(
          hubRepository.findAllByManagerId(hubManagerId)
      );
    }
    return GetHubsApplicationInternalResponseDto.from(
        hubRepository.findAllById(hubIds)
    );
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(
      cacheNames = "getRoutePaths",
      key = "'routespaths:sourceHubId:' + #dto.sourceHubId + ':destinationHubId:' + #dto.destinationHubId",
      cacheManager = "hubCacheManager")
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
  public UpdateHubRoutesApplicationResponseDto updateHubRoutes() {
    List<Hub> hubs = hubRepository.findAll();

    Map<UUID, HubRoute> hubRouteMap = hubs.stream()
        .flatMap(hub -> hub.getRoutesFromSource().stream())
        .collect(Collectors.toMap(HubRoute::getId, route -> route));

    Map<UUID, DirectionsApiApplicationResponseDto> directions = hubRouteMap.values().stream()
        .collect(Collectors.toMap(
            HubRoute::getId,
            route -> directionsApiClient.getDrivingRoute(
                formatCoordinates(route.getSourceHub()),
                formatCoordinates(route.getDestinationHub())
            )
        ));

    hubRouteMap.values().forEach(route -> {
      DirectionsApiApplicationResponseDto dto = directions.get(route.getId());
      route.update(dto.distanceMiters(), dto.durationMinutes());
    });

    return UpdateHubRoutesApplicationResponseDto.from(hubRouteMap.values());
  }

  private String formatCoordinates(Hub hub) {
    return String.format("%s,%s", hub.getLongitude(), hub.getLatitude());
  }

  @Override
  @Transactional
  public void deleteHub(DeleteHubApplicationRequestDto dto) {
    hubRepository.findById(dto.hubId()).orElseThrow(
        () -> CustomException.from(HubErrorCode.NOT_FOUND)
    ).delete(dto.deletedAt(), dto.deleterId());
  }
}