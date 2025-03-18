package com.faster.company.app.company.domain.entity;

import com.common.domain.BaseEntity;
import com.faster.company.app.company.domain.enums.CompanyType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id; // 회사 고유 식별 번호

  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID companyManagerId; // 업체 담당자 고유 식별 번호

  private String name; // 회사 이름

  private String contact; // 회사 연락처

  private String address; // 회사 주소

  private UUID hubId; // 회사 소속 허브 고유 식별 번호

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


