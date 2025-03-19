package com.faster.delivery.app.deliverymanager.infrastructure.client.dto;

import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubGetListResponseDto(
    List<HubResponseDto> hubInfos
) {
  @Builder
  public record HubResponseDto(
    UUID id,
    Long managerId,
    String name,
    String address,
    String latitude,
    String longitude,
    String createdBy,
    String createdAt
  ) {
  }

  public List<HubDto> toHubDtoList() {
    ArrayList<HubDto> hubDtoList = new ArrayList<>();
    for (HubResponseDto hubInfo : hubInfos) {
      HubDto hubDto = HubDto.builder()
          .hubId(hubInfo.id())
          .hubManagerId(hubInfo.managerId())
          .name(hubInfo.name())
          .address(hubInfo.address())
          .latitude(hubInfo.latitude())
          .longitude(hubInfo.longitude())
          .build();
      hubDtoList.add(hubDto);
    }
    return hubDtoList;
  }
}
