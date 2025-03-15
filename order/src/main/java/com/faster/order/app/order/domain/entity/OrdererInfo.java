package com.faster.order.app.order.domain.entity;

import com.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_orderer_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdererInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  private Order order;

  @Column(nullable = false, length = 100)
  private String receivingCompanyName;

  @Column(nullable = false, length = 200)
  private String receivingCompanyAddress;

  @Column(nullable = false, length = 100)
  private String receivingCompanyContact;
}
