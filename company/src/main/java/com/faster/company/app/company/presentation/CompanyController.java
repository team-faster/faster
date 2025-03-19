package com.faster.company.app.company.presentation;

import com.common.response.ApiResponse;
import com.faster.company.app.company.application.usecase.CompanyService;
import com.faster.company.app.company.presentation.dto.request.SaveCompanyRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/companies")
@RequiredArgsConstructor
@RestController
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createUser(
      @RequestBody @Valid SaveCompanyRequestDto requestDto) {
    // TODO: 업체 생성
    return null;
  }
}
