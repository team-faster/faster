package com.faster.company.app.company.infrastructure.persistence.jpa;

import static com.faster.company.app.company.domain.entity.QCompany.company;

import com.faster.company.app.company.application.dto.request.SearchCompaniesCondition;
import com.faster.company.app.company.domain.projection.SearchCompaniesProjection;
import com.faster.company.app.company.infrastructure.persistence.command.SearchCompaniesByConditionCommand;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CompanyJpaRepositoryCustomImpl implements CompanyJpaRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<SearchCompaniesProjection> searchCompaniesByCondition(
      SearchCompaniesCondition condition) {
    SearchCompaniesByConditionCommand command = SearchCompaniesByConditionCommand.from(condition);

    List<SearchCompaniesProjection> result = queryFactory.select(
            Projections.constructor(
                SearchCompaniesProjection.class,
                company.id,
                company.hubId,
                company.companyManagerId,
                company.name,
                company.contact,
                company.address,
                company.type,
                company.createdAt,
                company.updatedAt
            ))
        .from(company)
        .where(
            command.searchCondition()
        )
        .orderBy(command.getOrderSpecifiers())
        .fetch();

    Long totalCount = queryFactory
        .select(company.count())
        .from(company)
        .where(
            command.searchCondition()
        )
        .fetchOne();

    return PageableExecutionUtils.getPage(result, condition.pageable(), () -> totalCount);
  }
}
