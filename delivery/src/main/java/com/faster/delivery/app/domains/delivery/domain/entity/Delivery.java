package com.faster.delivery.app.domains.delivery.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Delivery {

  @Id
  private UUID id;
}
