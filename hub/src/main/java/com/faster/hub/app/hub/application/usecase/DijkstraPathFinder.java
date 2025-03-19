package com.faster.hub.app.hub.application.usecase;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto;
import com.faster.hub.app.hub.application.usecase.dto.response.GetPathsApplicationResponseDto.Path;
import com.faster.hub.app.hub.domain.entity.Hub;
import com.faster.hub.app.hub.domain.entity.HubRoute;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class DijkstraPathFinder implements PathFinder {

  @Override
  public GetPathsApplicationResponseDto findShortestPath(UUID sourceHubId, UUID destinationHubId, List<Hub> hubs) {
    Map<UUID, Hub> hubMap = hubs.stream().collect(Collectors.toMap(Hub::getId, hub -> hub));
    Map<UUID, Boolean> visited = hubs.stream().collect(Collectors.toMap(Hub::getId, hub -> false));

    if (hubMap.get(sourceHubId) == null || hubMap.get(destinationHubId) == null) {
      throw new CustomException(HubErrorCode.NOT_FOUND);
    }

    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(Node::totalDurationMinutes));
    pq.add(Node.of(sourceHubId, null, 0L, 0L, 0L));

    while (!pq.isEmpty()) {
      Node preNode = pq.poll();
      visited.put(preNode.hubId(), true);

      if (preNode.hubId().equals(destinationHubId)) {
        return buildPathResponse(preNode);
      }

      Long curTotalDurationMinutes = preNode.totalDurationMinutes();
      for (HubRoute curRoute : hubMap.get(preNode.hubId()).getRoutesFromSource()) {
        Hub nextHub = curRoute.getDestinationHub();
        if (visited.get(nextHub.getId())) continue;

        Long nextTotalDurationMinutes = curTotalDurationMinutes + curRoute.getDurationMinutes();
        pq.add(Node.of(nextHub.getId(), preNode, curRoute.getDistanceMeters(), curRoute.getDurationMinutes(), nextTotalDurationMinutes));
      }
    }

    throw new CustomException(HubErrorCode.ROUTE_NOT_FOUND);
  }

  private static GetPathsApplicationResponseDto buildPathResponse(Node destinationNode) {
    List<Path> paths = new ArrayList<>();
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
}
