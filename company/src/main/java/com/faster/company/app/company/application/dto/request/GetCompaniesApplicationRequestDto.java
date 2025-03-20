package com.faster.company.app.company.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetCompaniesApplicationRequestDto (
    Pageable pageable,
    UUID hubId,
    Long companyManagerId,
    String searchText,
    String nameSearchText,
    String contactSearchText,
    String addressSearchText,
    String type
){

  public static GetCompaniesApplicationRequestDto of(
      Pageable pageable,
      UUID hubId,
      Long companyManagerId,
      String searchText,
      String nameSearchText,
      String contactSearchText,
      String addressSearchText,
      String type
  ) {
    return GetCompaniesApplicationRequestDto.builder()
        .pageable(pageable)
        .hubId(hubId)
        .companyManagerId(companyManagerId)
        .searchText(searchText)
        .nameSearchText(nameSearchText)
        .contactSearchText(contactSearchText)
        .addressSearchText(addressSearchText)
        .type(type)
        .build();
  }
}
