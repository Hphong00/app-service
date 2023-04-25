package com.app.notificationservice.web.rest;


import com.app.notificationservice.service.EmailService;
import com.app.notificationservice.service.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/api")
public class EmailResource  {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/send-mail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    @KafkaListener(topics = "order", groupId = "orderservice") public void listenGroupFoo(String message) {
        EmailDetails details = new EmailDetails();
        details.setRecipient(message);
        details.setAttachment("1234");
        details.setSubject("1234");
        details.setMsgBody("1234");
        String status = emailService.sendSimpleMail(details);
        System.out.println("Received Message in group foo: " + status);
    }
    // Sending email with attachment
    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }

    @PostMapping("/send-email-multiple-recipients")
    public String sendEmailToMultipleRecipients(String to, String email) {
        String status = emailService.sendEmailToMultipleRecipients(to,email);
        return status;
    }
}
