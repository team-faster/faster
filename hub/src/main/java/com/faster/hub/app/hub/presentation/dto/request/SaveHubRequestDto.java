package com.faster.hub.app.hub.presentation.dto.request;

import com.faster.hub.app.hub.application.dto.request.SaveHubApplicationRequestDto;
import jakarta.validation.constraints.NotBlank;

public record SaveHubRequestDto(
    @NotBlank(message = "허브 이름을 입력해주세요.")
    String name,

    @NotBlank(message = "허브 주소를 입력해주세요.")
    String address,

    @NotBlank(message = "위도를 입력해주세요.")
    String latitude,

    @NotBlank(message = "경도를 입력해주세요.")
    String longitude

) {
    public SaveHubApplicationRequestDto toSaveHubApplicationRequestDto() {
        return SaveHubApplicationRequestDto.builder()
            .name(this.name)
            .address(this.address)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .build();
    }
}
