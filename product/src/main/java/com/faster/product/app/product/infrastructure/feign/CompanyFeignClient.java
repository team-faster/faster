package com.faster.product.app.product.infrastructure.feign;

import com.common.response.ApiResponse;
import com.faster.product.app.product.infrastructure.feign.dto.response.GetCompanyResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company-service")
public interface CompanyFeignClient {

  @GetMapping("/internal/companies")
  ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyByCompanyId(@RequestParam UUID companyId);

  @GetMapping("/internal/companies")
  ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyByCompanyMangerId(
      @RequestParam Long companyManagerId);
}
