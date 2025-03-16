package com.faster.delivery.app.domains.delivery.infrastructure.feign;

import com.common.response.ApiResponse;
import com.faster.delivery.app.domains.delivery.infrastructure.feign.dto.company.CompanyGetResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "company-service")
public interface CompanyClient {

  @GetMapping("/internal/companies/{companyId}")
  ApiResponse<CompanyGetResponseDto> getCompanyData(@PathVariable("companyId") UUID companyId);
}
