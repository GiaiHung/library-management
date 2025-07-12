package com.giaihung.commonservice.service;

import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
  private final JavaMailSender javaMailSender;
  private final Configuration freemarkerConfiguration;

  /**
   * @param to The recipient's email address
   * @param subject The subject of email
   * @param text Body of email
   * @param isHtml Is html format
   * @param attachment Attachment file. Can be null
   */
  public void sendEmail(String to, String subject, String text, boolean isHtml, File attachment) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(text, isHtml);
      if (attachment != null) {
        mimeMessageHelper.addAttachment(attachment.getName(), attachment);
      }
      javaMailSender.send(mimeMessage);
      log.info("Email sent successfully to {}", to);
    } catch (MessagingException exception) {
      log.error(exception.getMessage(), exception);
    }
  }

  /**
   * @param to The recipient's email address
   * @param subject The subject of email
   * @param templateName Body of email in HTML
   * @param placeholder A map of placeholders and their replacements
   * @param attachment Attachment file. Can be null
   */
  public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> placeholder, Resource attachment) {
    try {
      Template template = freemarkerConfiguration.getTemplate(templateName);
      String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, placeholder);
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(html, true);
      if (attachment != null) {
        mimeMessageHelper.addAttachment(attachment.getFilename() == null ? "images_" + Instant.now().getEpochSecond() : attachment.getFilename(), attachment);
      }
      javaMailSender.send(mimeMessage);
      log.info("Email with template sent successfully to {}", to);
    } catch (IOException | TemplateException | MessagingException e) {
        throw new RuntimeException(e);
    }
  }
}
