package com.faster.order.app.order.infrastructure;

import com.faster.order.app.order.application.ProductClient;
import com.faster.order.app.order.infrastructure.feign.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {
  private final ProductFeignClient productFeignClient;
}
