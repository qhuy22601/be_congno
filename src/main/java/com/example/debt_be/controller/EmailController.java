package com.example.debt_be.controller;

import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.EmailDetails;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.service.EmailService;
import io.micrometer.common.util.StringUtils;
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
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private DebtRepo debtRepo;

    @Value("${spring.mail.username}")
    private String sender;


    @PostMapping("/sendMailWithAttachment")
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

//    @PostMapping("/sendMailWithAttach")
//    public String sendMailWithAttachment(
//            @RequestParam("recipients") List<String> recipients,
//            @RequestParam("msgBody") String msgBody,
//            @RequestParam("subject") String subject,
//            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {
//
//        List<Debt> debts = getAll();
//        if (debts.isEmpty()) {
//            return "No debts found";
//        }
//
//        StringBuilder emailBody = new StringBuilder();
//        for (Debt debt : debts) {
//            String formattedDebtInfo = getFormattedDebtInfo(debt);
//            emailBody.append(formattedDebtInfo).append("\n\n");
//        }
//
//        return sendEmailsToDebtUsers(recipients, emailBody.toString(), subject, attachment);
//    }
//
//    private List<Debt> getAll() {
//        return debtRepo.findAll();
//    }
//
//    private String getFormattedDebtInfo(Debt debt) {
//        // Customize the formatting of debt information based on your requirements
//        String formattedDebtInfo = String.format(
//                "Debt Information:\n" +
//                        "Debt: %.2f\n" +
//                        "Pay: %.2f\n" +
//                        "Balance: %.2f\n" +
//                        "Date: %s",
//                debt.getDebt(), debt.getPay(), debt.getBalance(), debt.getDate()
//        );
//        return formattedDebtInfo;
//    }
//
//    private String sendEmailsToDebtUsers(List<String> recipients, String emailBody, String subject, MultipartFile attachment) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//
//            // Set recipients
//            for (String recipient : recipients) {
//                mimeMessageHelper.addTo(recipient);
//            }
//
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(emailBody);
//
//            if (attachment != null && !attachment.isEmpty()) {
//                mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
//            }
//
//            javaMailSender.send(mimeMessage);
//            return "Mail sent successfully";
//        } catch (MessagingException | IOException e) {
//            return "Error while sending mail";
//        }
//    }
//
//
//    private String getFormattedMessage(String msgBody) {
//        // Add your formatting logic here based on the structure of the debt information
//        // Example: String formattedMessage = "Debt Information:\n" + msgBody;
//        // Replace the above line with your custom formatting logic
//
//        return msgBody;
//    }


//    @PostMapping("/sendMailWithAttach")
//    public String sendMailWithAttachment(
//            @RequestParam(value= "subject" , defaultValue = "Công nợ") String subject,
//            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setSubject(subject);
//
//            List<Debt> debts = debtRepo.findAll(); // Retrieve all debts from the database
//
//            for (Debt debt : debts) {
//                UserModel user = debt.getUser();
//                String recipient = user.getContactEmail();
//
//                if (StringUtils.isNotBlank(recipient)) {
//                    String msgBody = "Gửi " + user.getName() + ",\n\n";
//                    msgBody += "Thông tin về công nợ của bạn:\n";
//                    msgBody += "Ngày: " + debt.getDate() + "\n";
//                    msgBody += "Ghi chú: " + debt.getNote() + "\n";
//                    msgBody += "Công nợ: " + debt.getDebt() + "\n";
//                    msgBody += "Thanh toán: " + debt.getPay() + "\n";
//                    msgBody += "Số dư: " + debt.getBalance() + "\n\n";
//                    msgBody += "Trân trọng.\n";
//
//                    mimeMessageHelper.setTo(recipient);
//                    mimeMessageHelper.setText(msgBody, true);
//
//                    if (attachment != null && !attachment.isEmpty()) {
//                        mimeMessageHelper.addAttachment(attachment.getOriginalFilename(),
//                                new ByteArrayResource(attachment.getBytes()));
//                    }
//
//                    javaMailSender.send(mimeMessage);
//                }
//            }
//
//            return "Mail sent successfully";
//        } catch (MessagingException | IOException e) {
//            return "Error while sending mail";
//        }
//    }


    @PostMapping("/sendMailWithAttach")
    public String sendMailWithAttachment(
            @RequestParam(value = "subject", defaultValue = "Công nợ") String subject,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        // Get all debts from the repository
        List<Debt> debts = debtRepo.findAll();

        // Iterate over each debt and send an email to the corresponding user
        for (Debt debt : debts) {
            UserModel user = debt.getUser();
            String recipient = user.getContactEmail();
            String msgBody = generateEmailBody(debt);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(recipient);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(msgBody, true);

                if (attachment != null && !attachment.isEmpty()) {
                    mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
                }

                javaMailSender.send(mimeMessage);
            } catch (MessagingException | IOException e) {
                return "Error while sending mail";
            }
        }

        return "Emails sent successfully";
    }

    private String generateEmailBody(Debt debt) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Gửi ").append(debt.getUser().getName()).append(",<br/><br/>");
        bodyBuilder.append("Thông tin về công nợ của bạn:<br/><br/>");
        bodyBuilder.append("Ngày: ").append(debt.getDate()).append("<br/>");
        bodyBuilder.append("Diễn Giải: ").append(debt.getNote()).append("<br/>");
        bodyBuilder.append("Công nợ: ").append(debt.getDebt()).append("<br/>");
        bodyBuilder.append("Thanh toán: ").append(debt.getPay()).append("<br/>");
        bodyBuilder.append("Số dư: ").append(debt.getBalance()).append("<br/><br/>");
        bodyBuilder.append("Trân trọng.");
        return bodyBuilder.toString();
    }

}
