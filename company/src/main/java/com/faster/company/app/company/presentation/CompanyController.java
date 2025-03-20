package com.faster.company.app.company.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.usecase.CompanyService;
import com.faster.company.app.company.presentation.dto.request.SaveCompanyRequestDto;
import com.faster.company.app.company.presentation.dto.response.GetCompanyResponseDto;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/companies")
@RequiredArgsConstructor
@RestController
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyById(
      @PathVariable UUID companyId) {

    GetCompanyApplicationResponseDto companyDto = companyService.getCompanyById(companyId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "업체 조회가 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            GetCompanyResponseDto.from(companyDto)));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> saveCompany(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody @Valid SaveCompanyRequestDto requestDto) {

    UUID companyId = companyService.saveCompany(userInfo, requestDto.toApplicationDto());
    return ResponseEntity.created(
            UriComponentsBuilder.fromUriString("/api/companies/{companyId}")
                .buildAndExpand(companyId)
                .toUri()
        )
        .body(new ApiResponse<>(
            "업체 생성이 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            null));
  }
}
