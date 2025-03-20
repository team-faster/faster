package com.faster.product.app.product.domain.criteria;

import static com.faster.product.app.global.utils.QuerydslUtil.nullSafeBuilder;
import static com.faster.product.app.product.domain.entity.QProduct.product;

import com.common.exception.CustomException;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.querydsl.core.BooleanBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchProductCriteria(
    UUID companyId,
    String companyName,
    String name,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    LocalDateTime startCreatedAt,
    LocalDateTime endCreatedAt,
    Boolean isDeleted
) {

  public BooleanBuilder eqCompanyId() {
    return nullSafeBuilder(() -> product.companyId.eq(companyId));
  }

  public BooleanBuilder likeCompanyName() {
    return nullSafeBuilder(() -> product.companyName.contains(companyName));
  }

  public BooleanBuilder likeName() {
    return nullSafeBuilder(() -> product.name.contains(name));
  }

  public BooleanBuilder betweenPrice() {
    if (minPrice == null && maxPrice == null)
      return new BooleanBuilder();
    if (minPrice != null && maxPrice != null)
      return new BooleanBuilder(product.price.between(minPrice, maxPrice));
    if (minPrice != null)
      return new BooleanBuilder(product.price.goe(minPrice));
    return new BooleanBuilder(product.price.loe(maxPrice));
  }

  public BooleanBuilder betweenPeriod() {
    if (startCreatedAt == null || endCreatedAt == null)
      return new BooleanBuilder();
    if (startCreatedAt.isAfter(endCreatedAt))
      throw new CustomException(ProductErrorCode.INVALID_PERIOD);
    return new BooleanBuilder(product.createdAt.between(startCreatedAt, endCreatedAt));
  }

  public BooleanBuilder checkIsDeleted() {
    if (isDeleted == null)
      return new BooleanBuilder();
    if (isDeleted)
      return new BooleanBuilder(product.deletedAt.isNotNull());
    if (!isDeleted)
      return new BooleanBuilder(product.deletedAt.isNull());
    return null;
  }
}
