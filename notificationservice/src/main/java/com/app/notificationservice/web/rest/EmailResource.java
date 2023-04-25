package com.app.notification.web.rest;

import com.app.notification.service.EmailService;
import com.app.notification.service.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailResource {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/send-mail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
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
