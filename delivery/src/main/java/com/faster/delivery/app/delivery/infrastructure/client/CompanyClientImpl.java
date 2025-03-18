package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
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
    ApiResponse<CompanyGetResponseDto> companyData = companyFeignClient.getCompanyData(companyId);
    CompanyGetResponseDto data = companyData.data();
    return data.toCompanyDto();
  }
}
