package com.giaihung.borrowingservice.config;

import com.giaihung.borrowingservice.command.event.CreateBorrowingEvent;
import com.giaihung.borrowingservice.command.event.DeleteBorrowingEvent;
import com.giaihung.commonservice.command.event.BookStatusUpdatedEvent;
import com.giaihung.commonservice.model.BookResponseModel;
import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public Serializer eventSerializer() {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[]{
                CreateBorrowingEvent.class,
                DeleteBorrowingEvent.class,
                BookStatusUpdatedEvent.class,
                BookResponseModel.class // Add BookResponseModel
        });
        // Optionally, allow all classes in your package (use cautiously)
        xStream.allowTypesByWildcard(new String[]{"com.giaihung.**"});
        return XStreamSerializer.builder().xStream(xStream).build();
    }

    @Bean
    public Serializer messageSerializer() {
        // Configure message serializer for queries and commands
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[]{
                CreateBorrowingEvent.class,
                DeleteBorrowingEvent.class,
                BookStatusUpdatedEvent.class,
                BookResponseModel.class // Add BookResponseModel
        });
        xStream.allowTypesByWildcard(new String[]{"com.giaihung.**"});
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}
