package com.faster.company.app.company.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record SearchCompaniesCondition(
    Pageable pageable,
    UUID hubId,
    Long companyManagerId,
    String searchText,
    String nameSearchText,
    String contactSearchText,
    String addressSearchText,
    String type
) {

  public static SearchCompaniesCondition from(GetCompaniesApplicationRequestDto dto) {
    return SearchCompaniesCondition.builder()
        .pageable(dto.pageable())
        .hubId(dto.hubId())
        .companyManagerId(dto.companyManagerId())
        .searchText(dto.searchText())
        .nameSearchText(dto.nameSearchText())
        .contactSearchText(dto.contactSearchText())
        .addressSearchText(dto.addressSearchText())
        .type(dto.type())
        .build();
  }
}
