package com.faster.company.app.company.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyService {

  IGetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId);

  IGetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(Long companyMangerId);

  UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto);
}
