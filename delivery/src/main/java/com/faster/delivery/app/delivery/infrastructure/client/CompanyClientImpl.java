package com.faster.delivery.app.delivery.infrastructure.client;

import com.faster.delivery.app.delivery.application.CompanyClient;
import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import com.faster.delivery.app.delivery.infrastructure.client.dto.company.CompanyGetResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CompanyClientImpl implements CompanyClient {

  private final CompanyFeignClient companyFeignClient;

  public CompanyDto getCompanyData(UUID companyId) {
    return companyFeignClient.getCompanyData(companyId).getBody().data().toCompanyDto();
  }

  @Override
  public CompanyDto getCompanyByManagerId(Long userId) {
    CompanyGetResponseDto companyDto = companyFeignClient.getCompanyByManagerId(userId).getBody().data();
    return companyDto.toCompanyDto();
  }
}
