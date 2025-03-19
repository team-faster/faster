package com.faster.company.app.company.application.usecase;

import com.common.exception.CustomException;
import com.faster.company.app.company.application.client.UserClient;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;
import com.faster.company.app.company.domain.entity.Company;
import com.faster.company.app.company.domain.repository.CompanyRepository;
import com.faster.company.app.global.exception.CompanyErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;
  private final UserClient userClient;

  @Override
  public GetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId) {

    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));
    GetUserApplicationResponseDto userDto = userClient.getUserById(company.getCompanyManagerId());
    return GetCompanyApplicationResponseDto.from(company, userDto);
  }

  @Override
  public GetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(
      Long companyMangerId) {

    Company company = companyRepository.findByCompanyManagerIdAndDeletedAtIsNull(companyMangerId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));
    GetUserApplicationResponseDto userDto = userClient.getUserById(company.getCompanyManagerId());
    return GetCompanyApplicationResponseDto.from(company, userDto);
  }

//  @Override
//  public SaveCompanyApplicationResponseDto saveCompany(SaveCompanyApplicationRequestDto requestDto) {
//
//    return null;
//  }
}
