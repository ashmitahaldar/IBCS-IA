package com.example.compsciia.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    private static final String HOST = Config.getProperty("SMTP_HOST");
    private static final String USERNAME = Config.getProperty("SMTP_USERNAME");
    private static final String PASSWORD = Config.getProperty("SMTP_PASSWORD");

    public static void sendEmail(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(USERNAME));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
