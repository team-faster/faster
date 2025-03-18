package com.faster.company.app.company.domain.entity;

import com.common.domain.BaseEntity;
import com.faster.company.app.company.domain.enums.CompanyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Table(name = "p_company")
@RequiredArgsConstructor
@Entity
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id; // 회사 고유 식별 번호

  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "company_manager_id", nullable = false)
  private UUID companyManagerId; // 업체 담당자 고유 식별 번호

  @Column(name = "name", nullable = false)
  private String name; // 회사 이름

  @Column(name = "contact", nullable = false)
  private String contact; // 회사 연락처

  @Column(name = "address", nullable = false)
  private String address; // 회사 주소

  @Column(name = "hub_id", nullable = false)
  private UUID hubId; // 회사 소속 허브 고유 식별 번호

  @Column(name = "type", nullable = false)
  private CompanyType type; // 회사 타입

  private Company(UUID companyManagerId, String name, String contact, String address, UUID hubId, CompanyType type) {
    this.companyManagerId = companyManagerId;
    this.name = name;
    this.contact = contact;
    this.address = address;
    this.hubId = hubId;
    this.type = type;
  }

  public static Company of(UUID companyManagerId, String name, String contact, String address, UUID hubId,
                           CompanyType type) {
    return new Company(companyManagerId, name, contact, address, hubId, type);
  }
}


