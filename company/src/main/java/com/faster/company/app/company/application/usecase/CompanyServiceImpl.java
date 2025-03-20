package com.faster.company.app.company.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.company.app.company.application.client.HubClient;
import com.faster.company.app.company.application.client.ProductClient;
import com.faster.company.app.company.application.client.UserClient;
import com.faster.company.app.company.application.dto.request.GetCompaniesApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.SaveCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.SearchCompaniesCondition;
import com.faster.company.app.company.application.dto.request.UpdateCompanyApplicationRequestDto;
import com.faster.company.app.company.application.dto.request.UpdateProductHubApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetCompaniesApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetHubsApplicationResponseDto.HubInfo;
import com.faster.company.app.company.application.dto.response.IGetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetUserApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.UpdateCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.UpdateProductHubApplicationResponseDto;
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
  private final ProductClient productClient;

  @Override
  public GetCompanyApplicationResponseDto getCompanyById(UUID companyId) {

    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));
    return GetCompanyApplicationResponseDto.from(company);
  }

  @Override
  public PageResponse<GetCompaniesApplicationResponseDto> getCompanies(
      GetCompaniesApplicationRequestDto dto) {
    return PageResponse.from(
        companyRepository.searchCompaniesByCondition(SearchCompaniesCondition.from(dto))
            .map(GetCompaniesApplicationResponseDto::from));
  }

  @Transactional
  @Override
  public UUID saveCompany(CurrentUserInfoDto userInfo, SaveCompanyApplicationRequestDto requestDto) {

    this.checkIfValidAccess(userInfo, requestDto.companyManagerId(), CompanyErrorCode.INVALID_SAVE_REQ);

    // 허브가 존재하는지 검증
    HubInfo hubDto = this.getHubById(requestDto.hubId(), CompanyErrorCode.INVALID_SAVE_REQ);

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
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_MANAGER));
    GetUserApplicationResponseDto userDto = userClient.getUserById(company.getCompanyManagerId());
    return IGetCompanyApplicationResponseDto.from(company, userDto);
  }

  @Override
  public UpdateCompanyApplicationResponseDto updateCompany(
      CurrentUserInfoDto userInfo, UpdateCompanyApplicationRequestDto updateDto) {

    Company company = companyRepository.findByIdAndDeletedAtIsNull(updateDto.companyId())
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));

    this.checkIfValidAccess(userInfo, company.getCompanyManagerId(), CompanyErrorCode.FORBIDDEN);

    // 허브가 존재하는지 검증
    if (updateDto.hubId() != company.getHubId()) {
      HubInfo hubDto = this.getHubById(updateDto.hubId(), CompanyErrorCode.FORBIDDEN);
      UpdateProductHubApplicationResponseDto updateResponse =
          productClient.updateProductHubByCompanyId(
          UpdateProductHubApplicationRequestDto.of(updateDto.companyId(), updateDto.hubId()));
    }

    company.update(updateDto.toCommand());
    return UpdateCompanyApplicationResponseDto.of(company.getId());
  }

  @Override
  public void deleteCompany(CurrentUserInfoDto userInfo, UUID companyId) {

    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_ID));

    this.checkIfValidAccess(userInfo, company.getCompanyManagerId(), CompanyErrorCode.FORBIDDEN);

    productClient.deleteProductByCompanyId(companyId);
    company.softDelete(userInfo.userId());
  }

  private void checkIfValidAccess(
      CurrentUserInfoDto userInfo, Long companyManagerId, CompanyErrorCode errorCode) {

    if (UserRole.ROLE_COMPANY == userInfo.role() && userInfo.userId() != companyManagerId) {
      throw new CustomException(errorCode);
    }
  }

  private HubInfo getHubById(UUID hubId, CompanyErrorCode errorCode) {

    return Optional.ofNullable(hubClient.getHubById(hubId))
        .flatMap(hub -> hub.hubInfos().stream().findFirst())
        .orElseThrow(() -> new CustomException(errorCode));
  }
}
