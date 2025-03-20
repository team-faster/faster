package com.faster.company.app.company.infrastructure.persistence.command;

import static com.faster.company.app.company.domain.entity.QCompany.company;
import static com.faster.company.app.company.infrastructure.persistence.util.QuerydslUtil.nullSafeBuilder;

import com.common.exception.CustomException;
import com.faster.company.app.company.application.dto.request.SearchCompaniesCondition;
import com.faster.company.app.company.domain.enums.CompanyType;
import com.faster.company.app.global.exception.CompanyErrorCode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Builder
public record SearchCompaniesByConditionCommand (
    Pageable pageable,
    UUID hubId,
    Long companyManagerId,
    String searchText,
    String nameSearchText,
    String contactSearchText,
    String addressSearchText,
    String type
){

  public static SearchCompaniesByConditionCommand from(SearchCompaniesCondition condition) {
    return SearchCompaniesByConditionCommand.builder()
        .pageable(condition.pageable())
        .hubId(condition.hubId())
        .companyManagerId(condition.companyManagerId())
        .searchText(condition.searchText())
        .nameSearchText(condition.nameSearchText())
        .contactSearchText(condition.contactSearchText())
        .addressSearchText(condition.addressSearchText())
        .type(condition.type())
        .build();
  }

  public BooleanBuilder searchCondition(){
    return eqHubId()
        .and(eqCompanyManagerId())
        .and(containsIgnoreCaseSearchText())
        .and(containsIgnoreCaseName(nameSearchText))
        .and(containsIgnoreCaseContact(contactSearchText))
        .and(containsIgnoreCaseAddress(addressSearchText))
        .and(eqCompanyType());
  }

  private BooleanBuilder eqHubId() {
    return nullSafeBuilder(() -> company.hubId.eq(hubId));
  }

  private BooleanBuilder eqCompanyManagerId() {
    return nullSafeBuilder(() -> company.companyManagerId.eq(companyManagerId));
  }

  private BooleanBuilder containsIgnoreCaseSearchText() {
    return containsIgnoreCaseName(searchText)
        .or(containsIgnoreCaseAddress(searchText))
        .or(containsIgnoreCaseContact(searchText));
  }

  private BooleanBuilder containsIgnoreCaseName(String text) {
    return nullSafeBuilder(() -> company.name.containsIgnoreCase(text));
  }

  private BooleanBuilder containsIgnoreCaseContact(String text) {
    return nullSafeBuilder(() -> company.contact.containsIgnoreCase(text));
  }

  private BooleanBuilder containsIgnoreCaseAddress(String text) {
    return nullSafeBuilder(() -> company.address.containsIgnoreCase(text));
  }

  private BooleanBuilder eqCompanyType() {
    return nullSafeBuilder(() -> company.type.eq(CompanyType.valueOf(type)));
  }

  public OrderSpecifier[] getOrderSpecifiers(){
    return pageable.getSort().stream()
        .map(order -> {
          Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
          return Arrays.stream(SortType.values())
              .filter(enumValue -> enumValue.checkIfMatched(order.getProperty()))
              .findAny()
              .orElseThrow(() -> new CustomException(CompanyErrorCode.INVALID_SORT_CONDITION))
              .getOrderSpecifier(direction);
        })
        .toArray(OrderSpecifier[]::new);
  }

  @RequiredArgsConstructor
  enum SortType{
    CREATEDAT((direction) -> new OrderSpecifier<>(direction, company.createdAt)),
    UPDATEDAT((direction) -> new OrderSpecifier<>(direction, company.updatedAt));

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
