package com.faster.hub.app.hub.presentation.dto.request;

import com.faster.hub.app.hub.application.usecase.dto.request.SaveHubApplicationRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveHubRequestDto(
    @NotNull(message = "허브 매니저 아이디를 입력해주세요.")
    Long managerId,

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
            .managerId(this.managerId)
            .name(this.name)
            .address(this.address)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .build();
    }
}
