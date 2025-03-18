package com.faster.order.app.order.application;

import com.faster.order.app.order.application.dto.response.GetCompanyApplicationResponseDto;
import java.util.UUID;

public interface CompanyClient {
  GetCompanyApplicationResponseDto getCompanyByCompanyId(UUID companyId);

  GetCompanyApplicationResponseDto getCompanyByCompanyManagerId(Long companyManagerId);
}
