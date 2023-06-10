package com.example.debt_be.controller;

import com.example.debt_be.entity.model.EmailDetails;
import com.example.debt_be.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details
//            ,@RequestParam(required = false) String attachmentPath
            )
    {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }


    @PostMapping("/sendMailWithAttach")
    public String sendMailWithAttachment(
            @RequestParam("recipient") String recipient,
            @RequestParam("msgBody") String msgBody,
            @RequestParam("subject") String subject,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(msgBody);

            if (attachment != null && !attachment.isEmpty()) {
                mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
            }

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";
        } catch (MessagingException | IOException e) {
            return "Error while sending mail";
        }
    }

}
