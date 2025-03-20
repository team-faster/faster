package com.faster.order.app.order.domain.criteria;

import static com.faster.order.app.global.utils.QuerydslUtil.nullSafeBuilder;
import static com.faster.order.app.order.domain.entity.QOrder.order;

import com.common.exception.CustomException;
import com.faster.order.app.global.exception.OrderErrorCode;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchOrderCriteria(
    BigDecimal minTotalPrice,
    BigDecimal maxTotalPrice,
    String supplierCompanyName,
    String receivingCompanyName,
    String name,
    String address,
    String contact,
    OrderStatus status,
    Boolean isDeleted,
    LocalDateTime startCreatedAt,
    LocalDateTime endCreatedAt
) {

  public BooleanBuilder likeSupplierCompanyName() {
    return nullSafeBuilder(() -> order.supplierCompanyName.contains(supplierCompanyName));
  }

  public BooleanBuilder likeReceivingCompanyName() {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyName.contains(receivingCompanyName));
  }

  public BooleanBuilder likeName() {
    return nullSafeBuilder(() -> order.name.contains(name));
  }

  public BooleanBuilder likeAddress() {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyAddress.contains(address));
  }

  public BooleanBuilder likeContact() {
    return nullSafeBuilder(() -> order.ordererInfo.receivingCompanyContact.contains(contact));
  }

  public BooleanBuilder eqStatus() {
    return nullSafeBuilder(() -> order.status.eq(status));
  }

  public BooleanBuilder betweenTotalPrice() {
    if (minTotalPrice == null && maxTotalPrice == null)
      return new BooleanBuilder();
    if (minTotalPrice != null && maxTotalPrice != null)
      return new BooleanBuilder(order.totalPrice.between(minTotalPrice, maxTotalPrice));
    if (minTotalPrice != null)
      return new BooleanBuilder(order.totalPrice.goe(minTotalPrice));
    return new BooleanBuilder(order.totalPrice.loe(maxTotalPrice));
  }

  public BooleanBuilder betweenPeriod() {
    if (startCreatedAt == null || endCreatedAt == null)
      return new BooleanBuilder();
    if (startCreatedAt.isAfter(endCreatedAt))
      throw new CustomException(OrderErrorCode.INVALID_PERIOD);
    return new BooleanBuilder(order.createdAt.between(startCreatedAt, endCreatedAt));
  }

  public BooleanBuilder checkIsDeleted() {
    if (isDeleted == null)
      return new BooleanBuilder();
    if (isDeleted)
      return new BooleanBuilder(order.deletedAt.isNotNull());
    if (!isDeleted)
      return new BooleanBuilder(order.deletedAt.isNull());
    return null;
  }
}
