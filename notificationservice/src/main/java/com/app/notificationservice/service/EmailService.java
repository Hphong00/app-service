package com.app.notification.service;


import com.app.notification.service.email.EmailDetails;

import java.util.List;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
    String sendEmailToMultipleRecipients(String email,String to);
}
