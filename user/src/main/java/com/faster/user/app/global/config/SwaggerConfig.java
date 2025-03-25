package com.faster.user.app.global.config;

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
    local.setUrl("http://localhost:10001");
    List<Server> serverList = new ArrayList<>();
    serverList.add(local);
    return new OpenAPI()
        .servers(serverList)
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .security(Collections.singletonList(securityRequirement))
        .info(new Info()
            .title("28조 FASTER: user-service")
            .description("<h3>인증/인가<h3>")
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
  public GroupedOpenApi userGroup() {
    return GroupedOpenApi.builder()
        .group("회원")
        .pathsToMatch("/api/auth/**", "/api/users/**")
        .build();
  }

  @Bean
  public GroupedOpenApi userInternalGroup() {
    return GroupedOpenApi.builder()
        .group("회원(Internal)")
        .pathsToMatch("/internal/auth/**", "/internal/users/**")
        .build();
  }
}
