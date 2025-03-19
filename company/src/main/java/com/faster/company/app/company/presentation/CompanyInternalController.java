package com.faster.company.app.company.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.response.ApiResponse;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.usecase.CompanyService;
import com.faster.company.app.company.presentation.dto.response.GetCompanyResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/internal/companies")
@RequiredArgsConstructor
@RestController
public class CompanyInternalController {
  private final CompanyService companyService;

  @AuthCheck
  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyById(
      @PathVariable UUID companyId) {

    GetCompanyApplicationResponseDto responseDto = companyService.getCompanyByIdInternal(companyId);
    return ResponseEntity.ok().body(
        ApiResponse.of(
            HttpStatus.OK,
            "업체 정보가 성공적으로 조회되었습니다.",
            GetCompanyResponseDto.from(responseDto)
        )
    );
  }

  @AuthCheck
  @GetMapping("/managers/{companyMangerId}")
  public ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyByCompanyManagerId(
      @PathVariable Long companyMangerId) {

    GetCompanyApplicationResponseDto responseDto =
        companyService.getCompanyByCompanyManagerIdInternal(companyMangerId);
    return ResponseEntity.ok().body(
        ApiResponse.of(
            HttpStatus.OK,
            "업체 정보가 성공적으로 조회되었습니다.",
            GetCompanyResponseDto.from(responseDto)
        )
    );
  }
}
