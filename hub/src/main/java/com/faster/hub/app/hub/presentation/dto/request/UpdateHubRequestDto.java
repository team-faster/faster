package com.faster.hub.app.hub.presentation.dto.request;

import com.faster.hub.app.hub.application.dto.request.UpdateHubApplicationRequestDto;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateHubRequestDto(
    @NotBlank(message = "허브 이름을 입력해주세요.")
    String name,

    @NotBlank(message = "허브 주소를 입력해주세요.")
    String address,

    @NotBlank(message = "위도를 입력해주세요.")
    String latitude,

    @NotBlank(message = "경도를 입력해주세요.")
    String longitude

) {

  public UpdateHubApplicationRequestDto toUpdateHubApplicationRequestDto(
      UUID hubId, UpdateHubRequestDto dto) {
    return UpdateHubApplicationRequestDto
        .builder()
        .id(hubId)
        .name(dto.name())
        .address(dto.address())
        .latitude(dto.latitude())
        .longitude(dto.longitude())
        .build();
  }
}