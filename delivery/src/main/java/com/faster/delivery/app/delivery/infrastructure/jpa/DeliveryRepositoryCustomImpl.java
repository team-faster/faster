package com.faster.delivery.app.delivery.infrastructure.jpa;

import static com.faster.delivery.app.delivery.domain.entity.QDelivery.delivery;
import static com.faster.delivery.app.delivery.domain.entity.QDeliveryRoute.deliveryRoute;
import static com.faster.delivery.app.global.common.QueryDslUtil.nullSafeBuilder;

import com.faster.delivery.app.delivery.domain.criteria.DeliveryCriteria;
import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.faster.delivery.app.delivery.domain.entity.DeliveryRoute;
import com.faster.delivery.app.delivery.domain.entity.QDeliveryRoute;
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

  public Page<Delivery> searchDeliveryList(DeliveryCriteria criteria, Pageable pageable) {
    // 정렬 기준 변환
    OrderSpecifier<?>[] orderSpecifiers = QueryDslUtil.getAllOrderSpecifierArr(pageable, delivery);

    BooleanBuilder booleanBuilder = getSearchCriteria(criteria);

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

  @Override
  public List<DeliveryRoute> findRoutesWithMissingManager() {
    QDeliveryRoute dr = QDeliveryRoute.deliveryRoute;
    QDeliveryRoute preDr = new QDeliveryRoute("preDr");

    return queryFactory
        .select(dr)
        .from(dr)
        .leftJoin(preDr)
        .on(dr.delivery.id.eq(preDr.delivery.id)
            .and(dr.sequence.subtract(1).eq(preDr.sequence)))
        .where(dr.deliveryManagerId.isNull()
            .and(preDr.deliveryManagerId.isNotNull().or(dr.sequence.eq(1))))
        .fetch();
  }

  private static BooleanBuilder getSearchCriteria(DeliveryCriteria criteria) {
    BooleanBuilder booleanBuilder = new BooleanBuilder()
        .and(nullSafeBuilder(() -> delivery.receiptCompanyId.eq(criteria.receiptCompanyId())))
        .and(nullSafeBuilder(() -> delivery.companyDeliveryManagerId.eq(criteria.companyDeliveryManagerId())))
        .and(
            nullSafeBuilder(() -> delivery.deliveryRouteList.any().deliveryManagerId.eq(criteria.hubDeliveryManagerId()))
        )
        .and(
            nullSafeBuilder(() -> delivery.deliveryRouteList.any().sourceHubId.eq(criteria.hubId()))
                .or(nullSafeBuilder(() -> delivery.deliveryRouteList.any().destinationHubId.eq(criteria.hubId())))
        )
        .and(nullSafeBuilder(() -> delivery.status.eq(criteria.status())));
    return booleanBuilder;
  }
}
