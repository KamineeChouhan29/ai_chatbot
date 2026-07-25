package com.ai_backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  //    public void sendEmail(String name,
  //                          String email,
  //                          String subject,
  //                          String message){
  //
  //        System.out.println("Inside Email Service");
  //        SimpleMailMessage mail = new SimpleMailMessage();
  //        mail.setFrom("kamineechouhan75@gmail.com");
  //
  //        mail.setTo("kamineechouhan75@gmail.com");
  //
  //        mail.setSubject(subject);
  //
  //        mail.setText(
  //                "Name : " + name +
  //                        "\nEmail : " + email +
  //                        "\nMessage : " + message
  //        );
  //
  //        System.out.println("Mail Successfully Sent");
  //
  //        mailSender.send(mail);
  //
  //        SimpleMailMessage userMail = new SimpleMailMessage();
  //
  //        userMail.setFrom("kamineechouhan75@gmail.com");
  //        userMail.setTo(email);   // User ke email par jayega
  //
  //        userMail.setSubject("We received your support request");
  //
  //        userMail.setText(
  //                "Dear " + name + ",\n\n" +
  //
  //                        "Thank you for contacting AI Assistant Support.\n\n" +
  //
  //                        "We have successfully received your request.\n" +
  //                        "Our support team will review your issue and get back to you as soon as
  // possible.\n\n" +
  //
  //                        "Thank you for your patience.\n\n" +
  //
  //                        "Best Regards,\n" +
  //                        "AI Assistant Support Team"
  //        );
  //
  //        mailSender.send(userMail);
  //
  //    }

  @Async
  public void sendEmail(String name, String email, String subject, String message) {
    System.out.println("Inside Email Service");
    try {
      SimpleMailMessage mail = new SimpleMailMessage();
      mail.setFrom("kamineechouhan75@gmail.com");
      mail.setTo("kamineechouhan75@gmail.com");
      mail.setReplyTo(email.trim()); // Allows admin to click "Reply" and message the user directly
      mail.setSubject("New Support Request: " + subject);
      mail.setText("Name : " + name + "\nEmail : " + email + "\nMessage : " + message);

      mailSender.send(mail);
      System.out.println("Admin Mail Successfully Sent");
    } catch (Exception e) {
      System.err.println("Error sending mail to admin: " + e.getMessage());
    }

    // 2. Doosra Mail: Jo User ko confirmation ke liye jayega
    try {
      jakarta.mail.internet.MimeMessage userMimeMessage = mailSender.createMimeMessage();
      org.springframework.mail.javamail.MimeMessageHelper helper =
          new org.springframework.mail.javamail.MimeMessageHelper(userMimeMessage, true, "UTF-8");

      helper.setFrom("kamineechouhan75@gmail.com");
      helper.setTo(email.trim());
      helper.setSubject("We received your support request");

      String htmlContent =
          "<div style=\"font-family: system-ui, sans-serif, Arial; font-size: 16px; color: #333;\">\n"
              + "  <a style=\"text-decoration: none; outline: none;\" href=\"https://ai-chatbot-brol.vercel.app\" target=\"_blank\">\n"
              + "    <strong>AI Assistant</strong>\n"
              + "  </a>\n"
              + "  <p style=\"padding-top: 16px; border-top: 1px solid #eaeaea;\">\n"
              + "    Hi "
              + name
              + ",\n"
              + "  </p>\n"
              + "  <p>\n"
              + "    👋 Thank you for contacting <strong>AI Assistant</strong>!\n"
              + "  </p>\n"
              + "  <p>\n"
              + "    We have successfully received your request:\n"
              + "  </p>\n"
              + "  <div style=\"background:#f5f5f5; padding:12px; border-radius:6px; border-left:4px solid #2563eb;\">\n"
              + "    <strong>"
              + message
              + "</strong>\n"
              + "  </div>\n"
              + "  <p style=\"margin-top:16px;\">\n"
              + "    Our AI Assistant team will review your request and respond as soon as possible. Most requests are handled within <strong>24–48 hours</strong>.\n"
              + "  </p>\n"
              + "  <p>\n"
              + "    Thank you for your patience and for using AI Assistant.\n"
              + "  </p>\n"
              + "  <p style=\"padding-top:16px; border-top:1px solid #eaeaea;\">\n"
              + "    Best regards,<br>\n"
              + "    <strong>AI Assistant Team</strong><br>\n"
              + "    🤖 Smart • Fast • Reliable\n"
              + "  </p>\n"
              + "</div>";

      helper.setText(htmlContent, true); // true sets it to HTML format

      mailSender.send(userMimeMessage);
      System.out.println("User Confirmation HTML Mail Successfully Sent");
    } catch (Exception e) {
      System.err.println("Error sending confirmation mail to user: " + e.getMessage());
    }
  }
}
