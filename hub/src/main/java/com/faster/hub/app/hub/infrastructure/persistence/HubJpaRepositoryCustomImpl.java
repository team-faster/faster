package com.faster.hub.app.hub.infrastructure.persistence;

import static com.faster.hub.app.hub.domain.entity.QHub.hub;

import com.faster.hub.app.hub.application.usecase.dto.request.SearchHubCondition;
import com.faster.hub.app.hub.domain.projection.SearchHubProjection;
import com.faster.hub.app.hub.infrastructure.persistence.command.SearchHubsByConditionCommand;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class HubJpaRepositoryCustomImpl implements HubJpaRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<SearchHubProjection> searchHubsByCondition(
      Pageable pageable, SearchHubCondition condition) {
    SearchHubsByConditionCommand command =
        SearchHubsByConditionCommand.of(pageable, condition);
    List<SearchHubProjection> result = queryFactory.select(
            Projections.constructor(
                SearchHubProjection.class,
                hub.id,
                hub.managerId,
                hub.name,
                hub.address,
                hub.latitude,
                hub.longitude,
                hub.createdAt,
                hub.updatedAt
            ))
        .from(hub)
        .where(
            command.searchCondition()
        )
        .orderBy(command.getOrderSpecifiers())
        .fetch();

    Long totalCount = queryFactory
        .select(hub.count())
        .from(hub)
        .where(
            command.searchCondition()
        )
        .fetchOne();

    return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
  }
}
