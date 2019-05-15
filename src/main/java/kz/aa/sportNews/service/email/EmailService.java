package kz.aa.sportNews.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
    void sendSimpleMessage(String subject,
                           String text,
                           String name) throws MessagingException;
}
