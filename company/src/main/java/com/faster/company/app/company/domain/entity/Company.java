package com.faster.company.app.company.domain.entity;

import com.common.domain.BaseEntity;
import com.faster.company.app.company.domain.command.UpdateCompanyCommand;
import com.faster.company.app.company.domain.enums.CompanyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company")
@Entity
public class Company extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id; // 회사 고유 식별 번호

  @Column(name = "hub_id", nullable = false)
  private UUID hubId; // 회사 소속 허브 고유 식별 번호

  @Column(name = "company_manager_id", nullable = false)
  private Long companyManagerId; // 업체 담당자 고유 식별 번호

  @Column(name = "name", nullable = false, length = 100)
  private String name; // 회사 이름

  @Column(name = "contact", nullable = false, length = 100)
  private String contact; // 회사 연락처

  @Column(name = "address", nullable = false, length = 200)
  private String address; // 회사 주소

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private CompanyType type; // 회사 타입

  @Builder
  private Company(UUID hubId, Long companyManagerId, String name, String contact, String address, CompanyType type) {
    this.hubId = hubId;
    this.companyManagerId = companyManagerId;
    this.name = name;
    this.contact = contact;
    this.address = address;
    this.type = type;
  }

  public static Company of(UUID hubId, Long companyManagerId, String name,
      String contact, String address, CompanyType type) {

    return Company.builder()
        .hubId(hubId)
        .companyManagerId(companyManagerId)
        .name(name)
        .contact(contact)
        .address(address)
        .type(type)
        .build();
  }

  public void update(UpdateCompanyCommand command) {
    this.hubId = command.hubId();
    this.name = command.name();
    this.contact = command.contact();
    this.address = command.address();
  }

  public void softDelete(Long userId) {
    LocalDateTime now = LocalDateTime.now();
    super.delete(now, userId);
  }
}


