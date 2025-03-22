package com.faster.product.app.product.application.client;

import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyClient {

  GetCompanyApplicationResponseDto getCompanyByCompanyId(UUID companyId);

  GetCompanyApplicationResponseDto getCompanyByCompanyManagerId(Long companyManagerId);
}
