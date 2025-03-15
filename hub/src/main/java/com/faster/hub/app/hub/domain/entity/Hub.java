package com.faster.hub.app.hub.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

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

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "address", length = 200, nullable = false)
  private String address;

  @Column(name = "latitude", length = 100, nullable = false)
  private String latitude;

  @Column(name = "longitude", length = 100, nullable = false)
  private String longitude;

  @Builder
  private Hub(String address, String latitude, String longitude, String name) {
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
  }
}
