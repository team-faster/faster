package com.faster.product.app.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients("com.faster.product.app.product.infrastructure.feign")
public class FeignClientConfig {
}