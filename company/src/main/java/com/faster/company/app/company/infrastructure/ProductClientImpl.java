package com.faster.company.app.company.infrastructure;

import com.faster.company.app.company.application.client.ProductClient;
import com.faster.company.app.company.application.dto.request.UpdateProductHubApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.UpdateProductHubApplicationResponseDto;
import com.faster.company.app.company.infrastructure.feign.ProductFeignClient;
import com.faster.company.app.company.infrastructure.feign.dto.request.UpdateProductHubRequestDto;
import com.faster.company.app.company.infrastructure.feign.dto.response.UpdateProductHubResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {
  private final ProductFeignClient productFeignClient;

  @Override
  public UpdateProductHubApplicationResponseDto updateProductHubByCompanyId(
      UpdateProductHubApplicationRequestDto updateDto) {

    UpdateProductHubResponseDto updateResponseDto =
        productFeignClient.updateProductHubByCompanyId(
        UpdateProductHubRequestDto.from(updateDto)).getBody().data();
    return updateResponseDto.toApplicationDto();
  }

  @Override
  public void deleteProductByCompanyId(UUID companyId) {
    productFeignClient.deleteProductByCompanyId(companyId).getBody().data();
  }
}
