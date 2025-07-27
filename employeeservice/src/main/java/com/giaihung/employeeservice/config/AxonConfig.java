package com.giaihung.employeeservice.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {
  @Bean
  @Primary
  public XStreamSerializer xStreamSerializer() {
    XStream xStream = new XStream();
    // Allow EmployeeDTO for deserialization
    // xStream.allowTypes(new Class<?>[] { EmployeeDTO.class });
    // Optionally, allow other types if needed
    xStream.allowTypesByWildcard(new String[] {"com.giaihung.**"});
    return XStreamSerializer.builder().xStream(xStream).build();
  }
}
