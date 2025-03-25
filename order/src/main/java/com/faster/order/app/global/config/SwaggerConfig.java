package com.faster.order.app.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER).name("Authorization");
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

    Server local = new Server();
    local.setUrl("http://localhost:12002");
    List<Server> serverList = new ArrayList<>();
    serverList.add(local);
    return new OpenAPI()
        .servers(serverList)
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .security(Collections.singletonList(securityRequirement))
        .info(new Info()
            .title("28조 FASTER: order-service")
            .description("<h3>주문<h3>")
            .version("1.0.0"));
  }

  @Bean
  public GroupedOpenApi group() {
    return GroupedOpenApi.builder()
        .group("전체")
        .pathsToMatch("/api/**", "/internal/**")
        .build();
  }

  @Bean
  public GroupedOpenApi orderGroup() {
    return GroupedOpenApi.builder()
        .group("주문")
        .pathsToMatch("/api/**")
        .build();
  }

  @Bean
  public GroupedOpenApi orderInternalGroup() {
    return GroupedOpenApi.builder()
        .group("주문(Internal)")
        .pathsToMatch("/internal/**")
        .build();
  }
}
