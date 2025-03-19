package com.faster.hub.app.global.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.faster.hub.app.hub.infrastructure.client.feign")
public class DirectionsApiClientConfig {
  @Value("${directions-api.client-id}")
  private String id;

  @Value("${directions-api.client-secret-key}")
  private String key;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("x-ncp-apigw-api-key-id", id);
      requestTemplate.header("x-ncp-apigw-api-key", key);
    };
  }
}
