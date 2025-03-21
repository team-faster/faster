package com.faster.message.app.message.infrastructure.fegin.dto.response;


public record IGetUserResponseDto(
    String name,
    String slackId
) {
}
