package kz.aa.sportNews.service.email;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendSimpleMessage(String subject, String text, String name) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("frankenbeker@gmail.com", "13154777");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("frankenbeker@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("aidos.jantleu@gmail.com"));
        msg.setSubject(subject);
        msg.setContent(text, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(new StringBuilder("Имя: " + name + "</br>" + text), "text/html");

        Transport.send(msg);
    }
}
