package com.giaihung.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Employee service",
            description = "Library Management Project - Employee Service",
            contact = @Contact(name = "Truong Giai Hung", email = "giaihung@gmail.com"),
            version = "0.0.0.1"),
    servers = {
      @Server(url = "http://localhost:8080", description = "DEV Environment"),
      @Server(url = "employee-service.prod.com", description = "PROD Environment")
    })
public class OpenApiConfig {}
