package com.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
    public static void main(String[] args) {
        String host = System.getenv("SMTP_HOST");
        String port = System.getenv("SMTP_PORT");
        String recipients = System.getenv("EMAIL_RECIPIENTS");
        String sender = System.getenv("EMAIL_SENDER");
        String username = System.getenv("SMTP_USERNAME");
        String emailBody = System.getenv("EMAIL_BODY");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, null);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject("Old Branches Report - " + java.time.LocalDate.now());
            message.setText(emailBody);

            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
