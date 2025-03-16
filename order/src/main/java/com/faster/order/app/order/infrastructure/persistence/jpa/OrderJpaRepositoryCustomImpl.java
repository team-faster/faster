package com.faster.order.app.order.infrastructure.persistence.jpa;


import static com.faster.order.app.global.utils.QuerydslUtil.nullSafeBuilder;
import static com.faster.order.app.order.domain.entity.QOrder.order;

import com.common.exception.CustomException;
import com.common.resolver.dto.UserRole;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.infrastructure.persistence.jpa.dto.response.OrderQuerydslResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class OrderJpaRepositoryCustomImpl implements OrderJpaRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<OrderQuerydslResponseDto> getOrdersByConditionAndCompanyId(
      Pageable pageable, SearchOrderConditionDto condition, UUID companyId, UserRole role) {

    OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

    List<OrderQuerydslResponseDto> dtoList = queryFactory
        .select(Projections.constructor(OrderQuerydslResponseDto.class,
            order.id, order.supplierCompanyId, order.supplierCompanyName,
            order.receivingCompanyId, order.ordererInfo.receivingCompanyName,
            order.deliveryId, order.name, order.totalPrice, order.status,
            order.createdAt, order.updatedAt))
        .from(order)
        .leftJoin(order.ordererInfo)
        .where(
            role == UserRole.ROLE_MASTER
                ? searchMasterCondition(condition)
                : searchCondition(condition)
        )
        .orderBy(orderSpecifiers)
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(order.count())
        .from(order)
        .where(
            role == UserRole.ROLE_MASTER
                ? searchMasterCondition(condition)
                : searchCondition(condition)
        )
        .orderBy(orderSpecifiers);

    return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
  }

  private BooleanBuilder searchMasterCondition(SearchOrderConditionDto condition) {
    return this.searchCondition(condition)
        .and(isDeleted(condition.isDeleted()));
  }


  private BooleanBuilder searchCondition(SearchOrderConditionDto condition) {
    return likeSupplierCompanyName(condition.supplierCompanyName())
        .and(likeReceivingCompanyName(condition.receivingCompanyName()))
        .and(likeName(condition.name()))
        .and(likeAddress(condition.address()))
        .and(likeContact(condition.contact()))
        .and(eqStatus(condition.status()))
        .and(betweenTotalPrice(condition.minTotalPrice(), condition.maxTotalPrice()))
        .and(betweenPeriod(condition.startCreatedAt(), condition.endCreatedAt()));
  }

  private BooleanBuilder likeSupplierCompanyName(String supplierCompanyName) {
    return nullSafeBuilder(() -> order.supplierCompanyName.contains(supplierCompanyName));
  }

  private BooleanBuilder likeReceivingCompanyName(String receivingCompanyName) {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyName.contains(receivingCompanyName));
  }

  private BooleanBuilder likeName(String name) {
    return nullSafeBuilder(() -> order.name.contains(name));
  }

  private BooleanBuilder likeAddress(String address) {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyAddress.contains(address));
  }

  private BooleanBuilder likeContact(String contact) {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyContact.contains(contact));
  }

  private BooleanBuilder eqStatus(OrderStatus status) {
    return nullSafeBuilder(() -> order.status.eq(status));
  }

  private BooleanBuilder betweenTotalPrice(BigDecimal min, BigDecimal max) {
    if (min == null && max == null)
      return new BooleanBuilder();
    if (min != null && max != null)
      return new BooleanBuilder(order.totalPrice.between(min, max));
    if (min != null)
      return new BooleanBuilder(order.totalPrice.goe(min));
    return new BooleanBuilder(order.totalPrice.loe(max));
  }

  private BooleanBuilder betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {
    if (startCreatedAt == null || endCreatedAt == null)
      return new BooleanBuilder();
    if (startCreatedAt.isAfter(endCreatedAt))
      throw new CustomException(OrderErrorCode.INVALID_PERIOD);
    return new BooleanBuilder(order.createdAt.between(startCreatedAt, endCreatedAt));
  }

  private BooleanBuilder isDeleted(Boolean isDeleted) {
    if (isDeleted == null)
      return new BooleanBuilder();
    if (isDeleted)
      return new BooleanBuilder(order.deletedAt.isNotNull());
    if (!isDeleted)
      return new BooleanBuilder(order.deletedAt.isNull());
    return null;
  }

  private OrderSpecifier[] createOrderSpecifiers(Sort sort) {

    return sort.stream()
        .map(order -> {
          com.querydsl.core.types.Order direction = order.getDirection().isAscending()
              ? com.querydsl.core.types.Order.ASC
              : com.querydsl.core.types.Order.DESC;
          return Arrays.stream(ReviewSortType.values())
              .filter(enumValue -> enumValue.checkIfMatched(order.getProperty()))
              .findAny()
              .orElseThrow(() -> new CustomException(OrderErrorCode.INVALID_SORT_CONDITION))
              .getOrderSpecifier(direction);
        })
        .toArray(OrderSpecifier[]::new);
  }

  @RequiredArgsConstructor
  enum ReviewSortType {
    TOTALPRICE((direction) -> new OrderSpecifier<>(direction, order.totalPrice)),
    CREATEDAT((direction) -> new OrderSpecifier<>(direction, order.createdAt)),
    MODIFIEDAT((direction) -> new OrderSpecifier<>(direction, order.updatedAt));

    private final Function<com.querydsl.core.types.Order, OrderSpecifier> typedOrderSpecifier;

    private OrderSpecifier<?> getOrderSpecifier(com.querydsl.core.types.Order direction) {
      return typedOrderSpecifier.apply(direction);
    }

    private boolean checkIfMatched(String property) {
      return this.name().equals(property
          .replaceAll("_", "")
          .toUpperCase());
    }
  }
}
