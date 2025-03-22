package com.faster.product.app.product.infrastructure;

import com.faster.product.app.product.application.client.CompanyClient;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.product.app.product.infrastructure.feign.CompanyFeignClient;
import com.faster.product.app.product.infrastructure.feign.dto.response.GetCompanyResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {
  private final CompanyFeignClient companyFeignClient;

  @Override
  public GetCompanyApplicationResponseDto getCompanyByCompanyId(UUID companyId) {
    GetCompanyResponseDto responseDto = companyFeignClient.getCompanyByCompanyId(companyId).getBody().data();
    return responseDto.toApplicationDto();
  }

  @Override
  public GetCompanyApplicationResponseDto getCompanyByCompanyManagerId(Long companyManagerId) {
    GetCompanyResponseDto responseDto = companyFeignClient.getCompanyByCompanyMangerId(companyManagerId).getBody().data();
    return responseDto.toApplicationDto();
  }
}
