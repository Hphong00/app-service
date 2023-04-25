package com.app.notificationservice.service;


import com.app.notificationservice.service.email.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
    String sendEmailToMultipleRecipients(String email,String to);
}
