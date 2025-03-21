package com.faster.message.app.message.application.dto.response;

import lombok.Builder;

@Builder
public record AGetUserResponseDto(
    String name,
    String slackId
) {

}
