package com.faster.company.app.company.application.usecase;

import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyService {
  //SaveCompanyResponseDto saveCompany(SaveCompanyRequestDto requestDto);

  GetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId);

  GetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(Long companyMangerId);
}
