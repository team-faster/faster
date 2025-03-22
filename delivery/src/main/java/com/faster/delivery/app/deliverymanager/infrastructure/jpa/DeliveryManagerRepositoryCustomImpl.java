package com.faster.delivery.app.deliverymanager.infrastructure.jpa;

import static com.faster.delivery.app.deliverymanager.domain.entity.QDeliveryManager.deliveryManager;
import static com.faster.delivery.app.global.common.QueryDslUtil.nullSafeBuilder;

import com.faster.delivery.app.deliverymanager.domain.criteria.DeliveryManagerCriteria;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.global.common.QueryDslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class DeliveryManagerRepositoryCustomImpl implements DeliveryManagerRepositoryCustom {

  private final EntityManager entityManager;
  private final JPAQueryFactory queryFactory;

  public Integer getNextDeliveryManagerSequence() {
    return ((Number) entityManager
        .createNativeQuery("SELECT nextval('delivery_manager_sequence_seq')")
        .getSingleResult())
        .intValue();
  }

  public Page<DeliveryManager> searchDeliveryManagerList(DeliveryManagerCriteria criteria, Pageable pageable) {
    // 정렬 기준 변환
    OrderSpecifier<?>[] orderSpecifiers = QueryDslUtil.getAllOrderSpecifierArr(pageable, deliveryManager);
    BooleanBuilder booleanBuilder = getSearchCriteria(criteria);

    List<DeliveryManager> contents = queryFactory.select(deliveryManager)
        .from(deliveryManager)
        .where(
            deliveryManager.deletedAt.isNull(),
            booleanBuilder
        )
        .orderBy(orderSpecifiers)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
    JPAQuery<Long> countQuery = queryFactory
        .select(deliveryManager.count())
        .from(deliveryManager)
        .where(
            deliveryManager.deletedAt.isNull(),
            booleanBuilder
        );

    return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
  }

  private static BooleanBuilder getSearchCriteria(DeliveryManagerCriteria criteria) {
    BooleanBuilder booleanBuilder = new BooleanBuilder()
        .and(nullSafeBuilder(() -> deliveryManager.hubId.in(criteria.hubIdList())))
        .and(nullSafeBuilder(() -> deliveryManager.userName.eq(criteria.searchUserName())))
        .and(nullSafeBuilder(() -> deliveryManager.id.eq(criteria.deliveryManagerId())));
    return booleanBuilder;
  }
}
