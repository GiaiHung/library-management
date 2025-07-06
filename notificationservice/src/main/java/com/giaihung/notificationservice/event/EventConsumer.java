package com.giaihung.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listenMessage(String message) {
        log.info("Received message: {}", message);
    }
}
