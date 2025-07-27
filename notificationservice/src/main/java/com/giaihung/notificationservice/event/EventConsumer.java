package com.giaihung.notificationservice.event;

import com.giaihung.commonservice.service.EmailService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
  private final EmailService emailService;

  @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
  @RetryableTopic(
      attempts = "4", // 3 retries and 1 dead letter queue (DLQ)
      backoff =
          @Backoff(delay = 1000, multiplier = 2), // Delay 1s for first time, 2s for second time
      autoCreateTopics = "true",
      dltStrategy =
          DltStrategy
              .FAIL_ON_ERROR, // Dead Letter Topic (DLT): No retry when message is put into DLQ
      include = {RuntimeException.class} // Only retry when runtime exception occurs
      )
  public void listenMessage(String message) {
    log.info("Received message: {}", message);
    // throw new RuntimeException("Error Test for retrying 4 times and put into dead letter queue");
  }

  @DltHandler
  public void handleDltMessage(@Payload String message) {
    log.info("DLT message: {}", message);
  }

  @KafkaListener(topics = "send_email", containerFactory = "kafkaListenerContainerFactory")
  public void listenEmail(String message) {
    log.info("Received email message: {}", message);
    String subject = "Kafka notification using email";
    String template =
        """
                <div>
                    <h1>Welcome, %s!</h1>
                    <p>Thank you for joining us. We're excited to have you on board.</p>
                    <p>Your username is: <strong>%s</strong></p>
                </div>""";
    String filledTemplate = String.format(template, "Giai Hung", message);
    emailService.sendEmail(message, subject, filledTemplate, true, null);
  }

  @KafkaListener(
      topics = "send_email_with_template",
      containerFactory = "kafkaListenerContainerFactory")
  public void listenEmailWithTemplate(String message) throws IOException {
    log.info("Received email template message: {}", message);
    Map<String, Object> placeholder = new HashMap<>();
    placeholder.put("name", "Giai Hung");
    // Use path relative to src/main/resources
    // ClassPathResource resource = new ClassPathResource("/images/nature.jpg");
    // File file = resource.getFile();
    Resource resource = new ClassPathResource("images/nature.jpg");
    emailService.sendEmailWithTemplate(
        message, "Celebrate Women's Equality Day!", "emailTemplate.ftl", placeholder, resource);
  }
}
