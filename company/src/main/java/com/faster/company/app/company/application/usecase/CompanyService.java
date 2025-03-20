package com.faster.company.app.company.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.UpdateCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.UpdateCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyService {

  GetCompanyApplicationResponseDto getCompanyById(UUID companyId);

  UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto);

  IGetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId);

  IGetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(Long companyMangerId);

  UpdateCompanyApplicationResponseDto updateCompany(CurrentUserInfoDto userInfo, UpdateCompanyApplicationRequestDto applicationDto);

  void deleteCompany(CurrentUserInfoDto userInfo, UUID companyId);
}
