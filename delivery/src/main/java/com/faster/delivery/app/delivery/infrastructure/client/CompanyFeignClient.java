package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.company.CompanyGetResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service")
public interface CompanyFeignClient {

  @GetMapping("/internal/companies/{companyId}")
  ApiResponse<CompanyGetResponseDto> getCompanyData(@PathVariable("companyId") UUID companyId);
}
