package com.faster.delivery.app.delivery.infrastructure.jpa;

import static com.faster.delivery.app.delivery.domain.entity.QDelivery.delivery;
import static com.faster.delivery.app.delivery.domain.entity.QDeliveryRoute.deliveryRoute;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.global.common.QueryDslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public Optional<Delivery> findByDeliveryId(UUID targetDeliveryId) {
    return Optional.ofNullable(
        queryFactory.select(delivery)
            .from(delivery)
            .join(delivery.deliveryRouteList, deliveryRoute).fetchJoin()
            .where(
                delivery.id.eq(targetDeliveryId)
                    .and(delivery.deletedAt.isNull())
            )
            .fetchOne()
    );
  }

  public Page<Delivery> searchDeliveryList(Pageable pageable, String role,
      UUID companyDeliveryManagerId,
      UUID receiptCompanyId,
      UUID hubId,
      Delivery.Status status
       ) {
    // 정렬 기준 변환
    OrderSpecifier<?>[] orderSpecifiers = QueryDslUtil.getAllOrderSpecifierArr(pageable, delivery);

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    switch (role) {
      case "ROLE_COMPANY" -> {
        booleanBuilder.and(delivery.receiptCompanyId.eq(receiptCompanyId));
      }
      case "ROLE_DELIVERY" -> {
        booleanBuilder.and(delivery.companyDeliveryManagerId.eq(companyDeliveryManagerId));
      }
      case "ROLE_HUB" -> {
        booleanBuilder.and(delivery.sourceHubId.eq(hubId).or(delivery.destinationHubId.eq(hubId)));
      }
    }

    if (status != null) {
      booleanBuilder.and(delivery.status.eq(status));
    }

    List<Delivery> contents = queryFactory.select(delivery)
        .from(delivery)
        .where(
            delivery.deletedAt.isNull(),
            booleanBuilder
        )
        .orderBy(orderSpecifiers)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
    JPAQuery<Long> countQuery = queryFactory
        .select(delivery.count())
        .from(delivery)
        .where(
          delivery.deletedAt.isNull(),
          booleanBuilder
        );
    return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
  }
}
