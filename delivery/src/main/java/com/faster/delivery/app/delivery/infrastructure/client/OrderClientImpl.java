package com.faster.delivery.app.delivery.infrastructure.client;

import com.faster.delivery.app.delivery.application.OrderClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

  private final OrderFeignClient orderFeignClient;

  public void temp() {

  }

}
