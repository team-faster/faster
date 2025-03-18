package com.faster.company.app.company.application.usecase;

import com.faster.company.app.company.application.dto.SaveCompanyRequestDto;
import com.faster.company.app.company.presentation.dto.SaveCompanyResponseDto;

public interface CompanyService {
  SaveCompanyResponseDto saveCompany(SaveCompanyRequestDto requestDto);
}
