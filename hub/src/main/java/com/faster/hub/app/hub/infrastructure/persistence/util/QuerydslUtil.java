package com.faster.hub.app.hub.infrastructure.persistence.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.function.Supplier;

public class QuerydslUtil {

  private QuerydslUtil() {
    throw new RuntimeException("Utility Class");
  }

  public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
    try {
      return new BooleanBuilder(f.get());
    } catch (Exception e) {
      return new BooleanBuilder();
    }
  }
}