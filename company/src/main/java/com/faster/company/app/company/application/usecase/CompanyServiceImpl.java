package com.faster.company.app.company.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.company.app.company.application.client.HubClient;
import com.faster.company.app.company.application.client.UserClient;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetHubsApplicationResponseDto.HubInfo;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;
import com.faster.company.app.company.domain.entity.Company;
import com.faster.company.app.company.domain.repository.CompanyRepository;
import com.faster.company.app.global.exception.CompanyErrorCode;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;
  private final UserClient userClient;
  private final HubClient hubClient;

  @Transactional
  @Override
  public UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto) {

    if (UserRole.ROLE_COMPANY == userInfo.role() && userInfo.userId() != requestDto.companyManagerId()) {
      throw new CustomException(CompanyErrorCode.INVALID_SAVE_REQ);
    }

    // 허브가 존재하는지 검증
    HubInfo hubDto = Optional.ofNullable(hubClient.getHubById(requestDto.hubId()))
        .flatMap(hub -> hub.hubInfos().stream().findFirst())
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_SAVE_REQ));

    Company company = requestDto.toEntity();
    companyRepository.save(company);
    return company.getId();
  }

  @Override
  public IGetCompanyApplicationResponseDto getCompanyByIdInternal(UUID companyId) {

    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));
    GetUserApplicationResponseDto userDto = userClient.getUserById(company.getCompanyManagerId());
    return IGetCompanyApplicationResponseDto.from(company, userDto);
  }

  @Override
  public IGetCompanyApplicationResponseDto getCompanyByCompanyManagerIdInternal(
      Long companyMangerId) {

    Company company = companyRepository.findByCompanyManagerIdAndDeletedAtIsNull(companyMangerId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));
    GetUserApplicationResponseDto userDto = userClient.getUserById(company.getCompanyManagerId());
    return IGetCompanyApplicationResponseDto.from(company, userDto);
  }
}
