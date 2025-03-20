package com.faster.company.app.company.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyService {

  GetCompanyApplicationResponseDto getCompanyById(UUID companyId);

  UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto);

  IGetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId);

  IGetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(Long companyMangerId);

}
