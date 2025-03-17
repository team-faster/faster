package com.faster.user.app.auth.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignInUserRequestDto(
    @NotBlank(message = "아이디: 필수 정보입니다.")
    @Pattern(
        regexp = "^[a-z0-9]{4,10}$",
        message = "아이디: 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)만 사용할 수 있습니다."
    )
    String username,

    @NotBlank(message = "비밀번호: 비밀번호를 입력해주세요.")
    @Pattern(
        regexp = "^[A-Za-z\\d@$!%*?&]{8,15}$",
        message = "비밀번호: 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 사용할 수 있습니다."
    )
    String password
) {
}
