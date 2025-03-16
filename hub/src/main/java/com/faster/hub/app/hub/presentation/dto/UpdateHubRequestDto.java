package com.faster.hub.app.hub.presentation.dto;

import jakarta.validation.constraints.NotBlank;

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

}