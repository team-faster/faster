package com.faster.product.app.product.fixture;

import com.faster.product.app.product.domain.entity.Product;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductFixture {

  public static Product createProduct(UUID hubId, UUID companyId, int i) {

    return Product.of(hubId, companyId, "회사명 " + i, "상품명 " + i,
        BigDecimal.valueOf(10000), 1000, i + "번째 상품입니다.");
  }
}
