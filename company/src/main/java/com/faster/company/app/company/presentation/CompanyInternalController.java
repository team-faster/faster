package com.faster.company.app.company.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.response.ApiResponse;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.usecase.CompanyService;
import com.faster.company.app.company.presentation.dto.response.IGetCompanyResponseDto;
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
  public ResponseEntity<ApiResponse<IGetCompanyResponseDto>> getCompanyById(
      @PathVariable UUID companyId) {

    IGetCompanyApplicationResponseDto responseDto = companyService.getCompanyByIdInternal(companyId);
    return ResponseEntity.ok().body(
        ApiResponse.of(
            HttpStatus.OK,
            "업체 정보가 성공적으로 조회되었습니다.",
            IGetCompanyResponseDto.from(responseDto)
        )
    );
  }

  @AuthCheck
  @GetMapping("/managers/{companyManagerId}")
  public ResponseEntity<ApiResponse<IGetCompanyResponseDto>> getCompanyByCompanyManagerId(
      @PathVariable Long companyManagerId) {

    IGetCompanyApplicationResponseDto responseDto =
        companyService.getCompanyByCompanyManagerIdInternal(companyManagerId);
    return ResponseEntity.ok().body(
        ApiResponse.of(
            HttpStatus.OK,
            "업체 정보가 성공적으로 조회되었습니다.",
            IGetCompanyResponseDto.from(responseDto)
        )
    );
  }
}
