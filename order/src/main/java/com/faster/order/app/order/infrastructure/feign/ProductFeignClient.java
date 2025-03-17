package com.faster.order.app.order.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

}
