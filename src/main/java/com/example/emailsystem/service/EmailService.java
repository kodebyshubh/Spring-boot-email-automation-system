package com.example.emailsystem.service;

import com.example.emailsystem.model.Employee;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sendEmailWithAttachment(Employee emp, byte[] pdfBytes) {
        final String from = "bohrashubh44@gmail.com";
        final String password = "cxmi ttpz ncfe zocg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emp.getEmail()));

            String subject;
            String fileName;
            String emailBody = "";

            String letterType = emp.getLetterType() != null ? emp.getLetterType().trim().toLowerCase() : "";

            if ("promotion".equals(letterType)) {
                subject = "Promotion Letter";
                fileName = "Promotion_Letter.pdf";
                emailBody = "<p>Dear <b>" + emp.getEmployeeName() + "</b>,</p>"
                        + "<p><b>Congratulations!</b> We are delighted to inform you that you have been promoted to "
                        + emp.getNewDesignation() + " in the " + emp.getDepartment()
                        + " department. Please find the attached promotion letter for details.</p>"
                        + "<p><b>Best Regards,<br>"
                        + "HR & Employee Services<br>"
                        + "company (India) Limited</b></p>";
            } else if ("appraisal".equals(letterType) || "partial_increment".equals(letterType)) {
                subject = "Appraisal Letter – FY 2025–26";
                fileName = "Appraisal_Letter.pdf";
                emailBody = "<p>Dear <b>" + emp.getEmployeeName() + "</b>,</p>"
                        + "<p><b>Congratulations on your appraisal for the year 2025–26.</b></p>"
                        + "<p>Please find attached your <b>Appraisal Letter</b> for the financial year 2025–26. "
                        + "This letter outlines the outcome of your performance review and related updates, if any, effective from <b>April 1, 2025</b>.</p>"
                        + "<p>We appreciate your continued efforts and contribution to the company Group and look forward to your ongoing support and growth within the organization.</p>"
                        + "<p>For any clarification, please feel free to connect with the HR department.</p>"
                        + "<p><b>Best Regards,<br>"
                        + "HR & Employee Services<br>"
                        + "company (India) Limited</b></p>";
            } else if ("no_increment".equals(letterType)) {
                subject = "Appraisal Letter – FY 2025–26";
                fileName = "Appraisal_Letter.pdf";
                emailBody = "<p>Dear <b>" + emp.getEmployeeName() + "</b>,</p>"
                        + "<p>We hope this message finds you well.</p>"
                        + "<p>Please find attached your <b>Appraisal Letter</b> for the financial year 2025–26. "
                        + "This letter outlines the outcome of your performance review and related updates, if any, effective from <b>April 1, 2025</b>.</p>"
                        + "<p>For any clarification, please feel free to connect with the HR department.</p>"
                        + "<p><b>Best Regards,<br>"
                        + "HR & Employee Services<br>"
                        + "company (India) Limited</b></p>";
            } else {
                subject = "Notification Letter";
                fileName = "Notification_Letter.pdf";
                emailBody = "<p>Dear <b>" + emp.getEmployeeName() + "</b>,</p>"
                        + "<p>Please find the attached letter for important information.</p>"
                        + "<p><b>Best Regards,<br>"
                        + "HR & Employee Services<br>"
                        + "Company name</b></p>";
            }

            message.setSubject(subject);

            // Email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(emailBody, "text/html; charset=utf-8");

            // PDF attachment
            MimeBodyPart pdfPart = new MimeBodyPart();
            pdfPart.setFileName(fileName);
            pdfPart.setContent(pdfBytes, "application/pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(pdfPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            System.err.println("Email send failed: " + e.getMessage());
        }
    }
}
