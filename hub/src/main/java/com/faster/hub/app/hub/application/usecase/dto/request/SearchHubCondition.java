package com.faster.hub.app.hub.application.usecase.dto.request;

import lombok.Builder;

@Builder
public record SearchHubCondition (
    String searchText, String nameSearchText, String addressSearchText){

  public static SearchHubCondition of(
      String searchText, String nameSearchText, String addressSearchText) {
    return SearchHubCondition.builder()
        .searchText(searchText)
        .nameSearchText(nameSearchText)
        .addressSearchText(addressSearchText)
        .build();
  }
}
