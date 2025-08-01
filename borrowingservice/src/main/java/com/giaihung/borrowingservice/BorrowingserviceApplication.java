package com.giaihung.borrowingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.giaihung.commonservice", "com.giaihung.borrowingservice"})
public class BorrowingserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BorrowingserviceApplication.class, args);
  }
}
