package com.giaihung.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.giaihung.commonservice", "com.giaihung.bookservice"})
public class BookserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookserviceApplication.class, args);
  }
}
