package com.faster.order.app.order.application.dto.request;

import com.faster.order.app.order.domain.criteria.SearchOrderCriteria;
import com.faster.order.app.order.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SearchOrderConditionDto(
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

  public static SearchOrderConditionDto of(
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
      LocalDateTime endCreatedAt) {

    return SearchOrderConditionDto.builder()
        .minTotalPrice(minTotalPrice)
        .maxTotalPrice(maxTotalPrice)
        .supplierCompanyName(supplierCompanyName)
        .receivingCompanyName(receivingCompanyName)
        .name(name)
        .address(address)
        .contact(contact)
        .status(status)
        .isDeleted(isDeleted)
        .startCreatedAt(startCreatedAt)
        .endCreatedAt(endCreatedAt)
        .build();
  }

  public SearchOrderCriteria toCriteria() {

    return SearchOrderCriteria.builder()
        .minTotalPrice(this.minTotalPrice)
        .maxTotalPrice(this.maxTotalPrice)
        .supplierCompanyName(this.supplierCompanyName)
        .receivingCompanyName(this.receivingCompanyName)
        .name(this.name)
        .address(this.address)
        .contact(this.contact)
        .status(this.status)
        .isDeleted(this.isDeleted)
        .startCreatedAt(this.startCreatedAt)
        .endCreatedAt(this.endCreatedAt)
        .build();
  }
}
