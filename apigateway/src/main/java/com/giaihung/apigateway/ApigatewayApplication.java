package com.giaihung.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApigatewayApplication.class, args);
  }

  @Bean
  public KeyResolver keyResolver() {
    // Differentiate each user by using user address
    return exchange ->
        Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
  }
}
