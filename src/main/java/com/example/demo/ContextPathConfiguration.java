package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebFilter;

/**
 * Spring MVC automatically configures via the property server.servlet.context-path but webflux
 * needs the below which is very annoying.
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnProperty("server.servlet.context-path")
@Configuration
public class ContextPathConfiguration {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public WebFilter contextPathWebFilter(ServerProperties serverProperties) {
    String contextPath = serverProperties.getServlet().getContextPath();
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String requestPath = request.getURI().getPath();
      if (requestPath.startsWith(contextPath + "/") || requestPath.equals(contextPath)) {
        return chain.filter(exchange.mutate().request(request.mutate().contextPath(contextPath).build()).build());

      } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }

    };
  }
}
