package com.faster.company.app.company.domain.repository;

import com.faster.company.app.company.domain.entity.Company;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {

  Optional<Company> findByIdAndDeletedAtIsNull(UUID companyId);

  Optional<Company> findByCompanyManagerIdAndDeletedAtIsNull(Long companyMangerId);

  Company save(Company company);
}
