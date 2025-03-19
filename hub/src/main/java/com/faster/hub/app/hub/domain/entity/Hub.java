package com.faster.hub.app.hub.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "p_hub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "hub_manager_id", nullable = false)
  private Long managerId;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "address", length = 200, nullable = false)
  private String address;

  @Column(name = "latitude", length = 100, nullable = false)
  private String latitude;

  @Column(name = "longitude", length = 100, nullable = false)
  private String longitude;

  @OneToMany(mappedBy = "sourceHub", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<HubRoute> routesFromSource;

  @OneToMany(mappedBy = "destinationHub", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<HubRoute> routesToDestination;

  @Builder
  private Hub(String address, String latitude, Long managerId, String longitude, String name,
      Set<HubRoute> routesFromSource, Set<HubRoute> routesToDestination) {
    this.address = address;
    this.latitude = latitude;
    this.managerId = managerId;
    this.longitude = longitude;
    this.name = name;
    this.routesFromSource = routesFromSource;
    this.routesToDestination = routesToDestination;
  }

  public Hub update(String name, String address, String latitude, String longitude) {
    this.name = name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    return this;
  }
}
