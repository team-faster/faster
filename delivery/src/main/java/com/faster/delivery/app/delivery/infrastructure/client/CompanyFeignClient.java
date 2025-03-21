package com.faster.delivery.app.delivery.infrastructure.client;

import com.common.response.ApiResponse;
import com.faster.delivery.app.delivery.infrastructure.client.dto.company.CompanyGetResponseDto;
import com.faster.delivery.app.global.config.FeignClientConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", configuration = FeignClientConfig.class)
public interface CompanyFeignClient {

  @GetMapping("/internal/companies/{companyId}")
  ResponseEntity<ApiResponse<CompanyGetResponseDto>> getCompanyData(@PathVariable("companyId") UUID companyId);

  @GetMapping("/internal/companies/managers/{companyMangerId}")
  ResponseEntity<ApiResponse<CompanyGetResponseDto>> getCompanyByManagerId(
      @PathVariable Long companyMangerId);
}
