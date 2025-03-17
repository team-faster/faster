package com.faster.order.app.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients("com.faster.order.app.order.infrastructure.feign")
public class FeignClientConfig {
}