package com.server.service;

import com.server.controller.CourseController;
import com.server.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class EmailService {
    private static final Logger LOG = LogManager.getLogger(CourseController.class.getName());

    private static final String username = "coursesmanagement5@gmail.com";

    private static final String password = "coursesmanagement2020";

    private static final ExecutorService sendMailExecutor = Executors.newCachedThreadPool();

    public void sendEmail(String textMessage, List<User> users) {
        LOG.info("Sending mail to students...");

        for (User user : users) {
            sendMailExecutor.execute(() -> sendMailAsync(textMessage, user.getEmail()));
        }
    }

    private void sendMailAsync(String textMessage, String recipient) {
        try {
            Message message = new MimeMessage(getMailSession());
            message.setFrom(new InternetAddress("courses.management@gmail.com"));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );
            message.setSubject("New notification from Courses management platform");
            message.setText(textMessage);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session getMailSession() {
        return Session.getInstance(loadProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Properties loadProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }
}
