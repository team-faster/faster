package com.faster.company.app.company.infrastructure.persistence.jpa;

import com.faster.company.app.company.application.dto.request.SearchCompaniesCondition;
import com.faster.company.app.company.domain.projection.SearchCompaniesProjection;
import org.springframework.data.domain.Page;

public interface CompanyJpaRepositoryCustom {
  Page<SearchCompaniesProjection> searchCompaniesByCondition(SearchCompaniesCondition condition);
}
