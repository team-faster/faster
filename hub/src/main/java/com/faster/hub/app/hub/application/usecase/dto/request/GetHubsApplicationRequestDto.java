package com.faster.hub.app.hub.application.usecase.dto.request;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetHubsApplicationRequestDto(
    Pageable pageable, String searchText, String nameSearchText, String addressSearchText){

  public static GetHubsApplicationRequestDto of(
      Pageable pageable, String searchText, String nameSearchText, String addressSearchText) {
    return GetHubsApplicationRequestDto.builder()
        .pageable(pageable)
        .searchText(searchText)
        .nameSearchText(nameSearchText)
        .addressSearchText(addressSearchText)
        .build();
  }
}
