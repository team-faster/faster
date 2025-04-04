package com.faster.hub.app.hub.infrastructure.client.feign;

import com.faster.hub.app.global.config.DirectionsApiClientConfig;
import com.faster.hub.app.hub.infrastructure.client.feign.dto.NaverDirectionsApiFeignClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-map-client", url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1", configuration = DirectionsApiClientConfig.class)
public interface NaverDirectionsApiFeignClient {

  @GetMapping("/driving")
  NaverDirectionsApiFeignClientResponse getDrivingRoute(@RequestParam("start") String start, @RequestParam("goal") String goal);
}