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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
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
  @Transactional(readOnly = true)
  public GetPathsApplicationResponseDto getPaths(GetPathApplicationRequestDto dto) {
    List<Hub> all = hubRepository.findAll();
    Map<UUID, Hub> hubMap = all.stream().collect(Collectors.toMap(Hub::getId, hub -> hub));
    Map<UUID, Boolean> visited = all.stream().collect(Collectors.toMap(Hub::getId, hub -> false));
    Hub source = hubMap.get(dto.sourceHubId());
    PriorityQueue<Node> pq = new PriorityQueue<>(
        Comparator.comparingLong(a -> a.curDurationMinutes()));
    pq.add(Node.of(source.getId(), null, 0L, 0L, 0L));

    while (!pq.isEmpty()) {
      Node preNode = pq.poll();

      visited.put(preNode.hubId(), true);
      if (preNode.hubId().equals(dto.destinationHubId())) {
        List<Path> paths = new ArrayList<>();
        Node destinationNode = preNode;
        while (destinationNode.preNode() != null) {
          UUID sourceHubId = destinationNode.preNode().hubId();
          UUID destinationHubId = destinationNode.hubId();
          Long distanceMeters = destinationNode.curDistanceMeters();
          Long durationMinutes = destinationNode.curDurationMinutes();
          paths.add(Path.of(sourceHubId, destinationHubId, distanceMeters, durationMinutes));
          destinationNode = destinationNode.preNode();
        }
        Collections.reverse(paths);
        return GetPathsApplicationResponseDto.of(paths);
      }
      Long curTotalDurationMinutes = preNode.totalDurationMinutes();
      for (HubRoute curRoute : hubMap.get(preNode.hubId()).getRoutesFromSource()) {
        Hub nextHub = curRoute.getDestinationHub();
        if(visited.get(nextHub.getId())) continue;
        Long nextTotalDurationMinutes = curTotalDurationMinutes + curRoute.getDurationMinutes();
        pq.add(Node.of(nextHub.getId(), preNode, curRoute.getDistanceMeters(), curRoute.getDurationMinutes(),  nextTotalDurationMinutes));
      }
    }

    throw new CustomException(HubErrorCode.ROUTE_NOT_FOUND);
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

@Builder
record Node(
    UUID hubId,
    Node preNode,
    Long curDistanceMeters,
    Long curDurationMinutes,
    Long totalDurationMinutes) {

  public static Node of(
      UUID hubId,
      Node preNode,
      Long curDistanceMeters,
      Long curDurationMinutes,
      Long totalDurationMinutes) {

    return Node.builder()
        .hubId(hubId)
        .preNode(preNode)
        .curDistanceMeters(curDistanceMeters)
        .curDurationMinutes(curDurationMinutes)
        .totalDurationMinutes(totalDurationMinutes)
        .build();
  }
}