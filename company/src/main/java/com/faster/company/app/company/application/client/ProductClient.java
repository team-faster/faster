package com.faster.company.app.company.application.client;

import com.faster.company.app.company.application.dto.request.UpdateProductHubApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.UpdateProductHubApplicationResponseDto;
import java.util.UUID;

public interface ProductClient {

  UpdateProductHubApplicationResponseDto updateProductHubByCompanyId(
      UpdateProductHubApplicationRequestDto updateDto);

  void deleteProductByCompanyId(UUID companyId);
}
