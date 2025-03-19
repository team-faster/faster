package com.faster.product.app.product.infrastructure.persistence.jpa;

import static com.faster.product.app.global.utils.QuerydslUtil.nullSafeBuilder;
import static com.faster.product.app.product.domain.entity.QProduct.product;

import com.common.exception.CustomException;
import com.common.resolver.dto.UserRole;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.domain.criteria.SearchProductCriteria;
import com.faster.product.app.product.domain.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
public class ProductJpaRepositoryCustomImpl implements ProductJpaRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Product> getProductsByConditionAndCompanyId(
      Pageable pageable, SearchProductCriteria condition, UUID companyId, UserRole role) {

    OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

    List<Product> dtoList = queryFactory
        .selectFrom(product)
        .where(
            role == UserRole.ROLE_MASTER
                ? searchMasterCondition(condition)
                : searchCondition(condition, companyId)
        )
        .orderBy(orderSpecifiers)
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(product.count())
        .from(product)
        .where(
            role == UserRole.ROLE_MASTER
                ? searchMasterCondition(condition)
                : searchCondition(condition, companyId)
        )
        .orderBy(orderSpecifiers);

    return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
  }

  private BooleanBuilder searchMasterCondition(SearchProductCriteria condition) {
    return this.searchCondition(condition, null)
        .and(isDeleted(condition.isDeleted()));
  }

  private BooleanBuilder searchCondition(SearchProductCriteria condition, UUID companyId) {
    return likeCompanyName(condition.companyName())
        .and(likeName(condition.name()))
        .and(betweenPrice(condition.minPrice(), condition.maxPrice()))
        .and(betweenPeriod(condition.startCreatedAt(), condition.endCreatedAt()))
        .and(eqCompanyId(companyId))
        .and(eqCompanyId(condition.companyId()));
  }

  private BooleanBuilder eqCompanyId(UUID companyId) {
    return nullSafeBuilder(() -> product.companyId.eq(companyId));
  }

  private BooleanBuilder likeCompanyName(String companyName) {
    return nullSafeBuilder(() -> product.companyName.contains(companyName));
  }

  private BooleanBuilder likeName(String name) {
    return nullSafeBuilder(() -> product.name.contains(name));
  }

  private BooleanBuilder betweenPrice(BigDecimal min, BigDecimal max) {
    if (min == null && max == null)
      return new BooleanBuilder();
    if (min != null && max != null)
      return new BooleanBuilder(product.price.between(min, max));
    if (min != null)
      return new BooleanBuilder(product.price.goe(min));
    return new BooleanBuilder(product.price.loe(max));
  }

  private BooleanBuilder betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {
    if (startCreatedAt == null || endCreatedAt == null)
      return new BooleanBuilder();
    if (startCreatedAt.isAfter(endCreatedAt))
      throw new CustomException(ProductErrorCode.INVALID_PERIOD);
    return new BooleanBuilder(product.createdAt.between(startCreatedAt, endCreatedAt));
  }

  private BooleanBuilder isDeleted(Boolean isDeleted) {
    if (isDeleted == null)
      return new BooleanBuilder();
    if (isDeleted)
      return new BooleanBuilder(product.deletedAt.isNotNull());
    if (!isDeleted)
      return new BooleanBuilder(product.deletedAt.isNull());
    return null;
  }

  private OrderSpecifier[] createOrderSpecifiers(Sort sort) {

    return sort.stream()
        .map(order -> {
          Order direction = order.getDirection().isAscending()
              ? Order.ASC
              : Order.DESC;
          return Arrays.stream(ReviewSortType.values())
              .filter(enumValue -> enumValue.checkIfMatched(order.getProperty()))
              .findAny()
              .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_SORT_CONDITION))
              .getOrderSpecifier(direction);
        })
        .toArray(OrderSpecifier[]::new);
  }

  @RequiredArgsConstructor
  enum ReviewSortType {
    PRICE((direction) -> new OrderSpecifier<>(direction, product.price)),
    CREATEDAT((direction) -> new OrderSpecifier<>(direction, product.createdAt)),
    MODIFIEDAT((direction) -> new OrderSpecifier<>(direction, product.updatedAt));

    private final Function<Order, OrderSpecifier> typedOrderSpecifier;

    private OrderSpecifier<?> getOrderSpecifier(Order direction) {
      return typedOrderSpecifier.apply(direction);
    }

    private boolean checkIfMatched(String property) {
      return this.name().equals(property
          .replaceAll("_", "")
          .toUpperCase());
    }
  }
}
