package com.faster.hub.app.hub.infrastructure.persistence.command;

import static com.faster.hub.app.hub.domain.entity.QHub.hub;
import static com.faster.hub.app.hub.infrastructure.persistence.util.QuerydslUtil.nullSafeBuilder;

import com.common.exception.CustomException;
import com.faster.hub.app.global.exception.HubErrorCode;
import com.faster.hub.app.hub.application.usecase.dto.request.SearchHubCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Builder
public record SearchHubsByConditionCommand (
    Pageable pageable, String searchText, String nameSearchText, String addressSearchText
){

  public static SearchHubsByConditionCommand of(Pageable pageable, SearchHubCondition condition) {
    return SearchHubsByConditionCommand.builder()
        .pageable(pageable)
        .searchText(condition.searchText())
        .nameSearchText(condition.nameSearchText())
        .addressSearchText(condition.addressSearchText())
        .build();
  }

  public BooleanBuilder searchCondition(){
    return containsIgnoreCaseSearchText()
        .and(containsIgnoreCaseName(nameSearchText))
        .and(containsIgnoreCaseAddress(addressSearchText));
  }

  private BooleanBuilder containsIgnoreCaseSearchText() {
    return containsIgnoreCaseName(searchText)
        .or(containsIgnoreCaseAddress(searchText));
  }

  private BooleanBuilder containsIgnoreCaseName(String text) {
    return nullSafeBuilder(() -> hub.name.containsIgnoreCase(text));
  }
  private BooleanBuilder containsIgnoreCaseAddress(String text) {
    return nullSafeBuilder(() -> hub.name.containsIgnoreCase(text));
  }

  public OrderSpecifier[] getOrderSpecifiers(){
    return pageable.getSort().stream()
        .map(order -> {
          Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
          return Arrays.stream(SortType.values())
              .filter(enumValue -> enumValue.checkIfMatched(order.getProperty()))
              .findAny()
              .orElseThrow(() -> new CustomException(HubErrorCode.INVALID_SORT_CONDITION))
              .getOrderSpecifier(direction);
        })
        .toArray(OrderSpecifier[]::new);
  }

  @RequiredArgsConstructor
  enum SortType{
    CREATEDAT((direction) -> new OrderSpecifier<>(direction, hub.createdAt)),
    MODIFIEDAT((direction) -> new OrderSpecifier<>(direction, hub.updatedAt));

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
