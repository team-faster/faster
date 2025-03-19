package com.faster.hub.app.hub.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub_route")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_hub_id", nullable = false)
  private Hub sourceHub;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "destination_hub_id", nullable = false)
  private Hub destinationHub;

  @Column(name = "distance_m")
  private Long distanceMeters;

  @Column(name = "duration_min")
  private Long durationMinutes;

  @Builder
  private HubRoute(Hub sourceHub, Hub destinationHub, Long distanceMeters, Long durationMinutes) {
    setSourceHub(sourceHub);
    setDestinationHub(destinationHub);
    this.distanceMeters = distanceMeters;
    this.durationMinutes = durationMinutes;
  }

  public HubRoute update(Long distanceMeters, Long durationMinutes){
    this.distanceMeters = distanceMeters;
    this.durationMinutes = durationMinutes;
    return this;
  }

  private void setSourceHub(Hub sourceHub) {
    this.sourceHub = sourceHub;
    if (!sourceHub.getRoutesFromSource().contains(this)) {
      sourceHub.getRoutesFromSource().add(this);
    }
  }

  private void setDestinationHub(Hub destinationHub) {
    this.destinationHub = destinationHub;
    if (!destinationHub.getRoutesToDestination().contains(this)) {
      destinationHub.getRoutesToDestination().add(this);
    }
  }
}

