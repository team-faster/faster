package com.faster.company.app.company.infrastructure.feign;

import com.common.response.ApiResponse;
import com.faster.company.app.company.infrastructure.feign.dto.request.UpdateProductHubRequestDto;
import com.faster.company.app.company.infrastructure.feign.dto.response.UpdateProductHubResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

  @PatchMapping("/internal/products/hub")
  ResponseEntity<ApiResponse<UpdateProductHubResponseDto>> updateProductHubByCompanyId(
      @RequestBody UpdateProductHubRequestDto updateDto);

  @DeleteMapping("/internal/products")
  ResponseEntity<ApiResponse<Void>> deleteProductByCompanyId(@RequestParam(name="company-id") UUID companyId);
}
