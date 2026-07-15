package com.ai_backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;


    public EmailService(JavaMailSender mailSender){
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
//                        "Our support team will review your issue and get back to you as soon as possible.\n\n" +
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

    public void sendEmail(String name, String email, String subject, String message){
        System.out.println("Inside Email Service");
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("kamineechouhan75@gmail.com");
            mail.setTo("kamineechouhan75@gmail.com");
            mail.setSubject("New Support Request: " + subject);
            mail.setText(
                    "Name : " + name +
                            "\nEmail : " + email +
                            "\nMessage : " + message
            );

            mailSender.send(mail);
            System.out.println("Admin Mail Successfully Sent");
        } catch (Exception e) {
            System.err.println("Error sending mail to admin: " + e.getMessage());
        }

        // 2. Doosra Mail: Jo User ko confirmation ke liye jayega
        try {
            SimpleMailMessage userMail = new SimpleMailMessage();
            userMail.setFrom("kamineechouhan75@gmail.com");
            userMail.setTo(email.trim());   // .trim() lagane se extra spaces hat jayengi

            userMail.setSubject("We received your support request");
            userMail.setText(
                    "Dear " + name + ",\n\n" +
                            "Thank you for contacting AI Assistant Support.\n\n" +
                            "We have successfully received your request.\n" +
                            "Our support team will review your issue and get back to you as soon as possible.\n\n" +
                            "Thank you for your patience.\n\n" +
                            "Best Regards,\n" +
                            "AI Assistant Support Team"
            );

            mailSender.send(userMail);
            System.out.println("User Confirmation Mail Successfully Sent");
        } catch (Exception e) {
            System.err.println("Error sending confirmation mail to user: " + e.getMessage());
        }
    }

}
