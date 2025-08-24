package com.example.emailsystem.controller;

import com.example.emailsystem.model.Employee;
import com.example.emailsystem.service.CSVProcessor;
import com.example.emailsystem.service.EmailService;
import com.example.emailsystem.service.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PDFGenerator pdfGenerator;

    @GetMapping("/")
    public String showUploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {

        if (file.isEmpty()) {
            model.addAttribute("message", "❌ Please select a file to upload.");
            return "upload";
        }

        int sent = 0;
        int skipped = 0;
        List<String> skippedIds = new ArrayList<>();

        try {
            List<Employee> employees = CSVProcessor.parseExcel(file);
            System.out.println("📊 Employees Found: " + employees.size());

            for (Employee emp : employees) {
                String pms = emp.getPmsRating() != null ? emp.getPmsRating().trim().toLowerCase() : "";

                // Normalize PMS Rating check
                String normalizedPms = pms.trim().toLowerCase();

                if (normalizedPms.contains("does not") || normalizedPms.contains("not meet")) {
                    emp.setLetterType("no_increment");
                } else if (normalizedPms.contains("partial")) {
                    emp.setLetterType("partial_increment");
                } else if (normalizedPms.contains("significantly exceed") ||
                        normalizedPms.contains("exceed") ||
                        normalizedPms.contains("meet")) {
                    emp.setLetterType("appraisal");
                } else {
                    System.out.println("⚠️ Skipping Employee due to Unknown PMS Rating → " +
                            "ID=" + emp.getEmpId() +
                            ", Name=" + emp.getEmployeeName() +
                            ", Email=" + emp.getEmail() +
                            ", PMS=" + emp.getPmsRating());
                    skipped++;
                    skippedIds.add(emp.getEmpId());
                    continue;
                }

                if (emp.getEmail() == null || emp.getEmail().isBlank()) {
                    System.out.println("⚠️ Skipping Employee due to Missing Email → " +
                            "ID=" + emp.getEmpId() +
                            ", Name=" + emp.getEmployeeName() +
                            ", PMS=" + emp.getPmsRating() +
                            ", LetterType=" + emp.getLetterType());
                    skipped++;
                    skippedIds.add(emp.getEmpId());
                    continue;
                }

                // Debug before sending email
                System.out.println("✅ Ready to send email → " +
                        "ID=" + emp.getEmpId() +
                        ", Name=" + emp.getEmployeeName() +
                        ", Email=" + emp.getEmail() +
                        ", PMS=" + emp.getPmsRating() +
                        ", LetterType=" + emp.getLetterType());

                try {
                    byte[] pdf = pdfGenerator.generatePDF(emp);
                    emailService.sendEmailWithAttachment(emp, pdf);
                    sent++;
                } catch (Exception e) {
                    System.out.println("❌ Error sending email → " +
                            "ID=" + emp.getEmpId() +
                            ", Name=" + emp.getEmployeeName() +
                            ", Reason=" + e.getMessage());
                    skipped++;
                    skippedIds.add(emp.getEmpId());
                }
            }

            model.addAttribute("message", "✅ Emails processed successfully.");
            model.addAttribute("totalSent", sent);
            model.addAttribute("totalSkipped", skipped);
            model.addAttribute("skippedIds", skippedIds);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "❌ Failed to process file: " + e.getMessage());
        }

        return "upload"; // ✅ Stay on the same page instead of redirect
    }
}
