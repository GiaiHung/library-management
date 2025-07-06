package com.giaihung.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "4", // 3 retries and 1 dead letter queue (DLQ)
            backoff = @Backoff(delay = 1000, multiplier = 2), // Delay 1s for first time, 2s for second time
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR, // Dead Letter Topic (DLT): No retry when message is put into DLQ
            include = { RuntimeException.class } // Only retry when runtime exception occurs
    )
    public void listenMessage(String message) {
        log.info("Received message: {}", message);
        // throw new RuntimeException("Error Test for retrying 4 times and put into dead letter queue");
    }

    @DltHandler
    public void handleDltMessage(@Payload String message) {
        log.info("DLT message: {}", message);
    }
}
