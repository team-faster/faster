package com.faster.company.app.company.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.PageResponse;
import com.faster.company.app.company.application.dto.request.GetCompaniesApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.UpdateCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetCompaniesApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.UpdateCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyService {

  GetCompanyApplicationResponseDto getCompanyById(UUID companyId);

  PageResponse<GetCompaniesApplicationResponseDto> getCompanies(GetCompaniesApplicationRequestDto dto);

  UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto);

  IGetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId);

  IGetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(Long companyMangerId);

  UpdateCompanyApplicationResponseDto updateCompany(CurrentUserInfoDto userInfo, UpdateCompanyApplicationRequestDto applicationDto);

  void deleteCompany(CurrentUserInfoDto userInfo, UUID companyId);
}
