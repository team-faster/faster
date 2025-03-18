package com.faster.delivery.app.delivery.application;

import com.faster.delivery.app.delivery.application.dto.CompanyDto;
import java.util.UUID;

public interface CompanyClient {
  CompanyDto getCompanyData(UUID companyId);
}
