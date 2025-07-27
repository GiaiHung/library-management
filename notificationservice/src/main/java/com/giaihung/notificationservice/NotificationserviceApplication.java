package com.giaihung.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.giaihung.commonservice", "com.giaihung.notificationservice"})
public class NotificationserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationserviceApplication.class, args);
  }
}
