package com.dev.voyagewell.service.mail;

import javax.mail.MessagingException;

public interface MailService {
    void sendEmail(
            String from,
            String to,
            String subject,
            String content
    ) throws MessagingException;
}
