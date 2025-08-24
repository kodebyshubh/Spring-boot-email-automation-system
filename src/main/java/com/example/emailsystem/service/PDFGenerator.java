package com.example.emailsystem.service;

import com.example.emailsystem.model.Employee;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGenerator {

        public byte[] generatePDF(Employee emp) throws Exception {
                Document doc = new Document(PageSize.A4, 50, 50, 80, 50);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(doc, out);

                writer.setPageEvent(new FooterPageEvent()); // Footer at bottom of every page
                doc.open();

                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
                Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
                Font linkFont = new Font(Font.FontFamily.HELVETICA, 11, Font.UNDERLINE, BaseColor.BLUE);

                Image logo = Image.getInstance(java.net.URI.create(
                                "https://www.mintformations.co.uk/blog/wp-content/uploads/2020/05/shutterstock_583717939-1.jpg")
                                .toURL());
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                doc.add(logo);
                doc.add(Chunk.NEWLINE);

                String letterType = emp.getLetterType() != null ? emp.getLetterType().trim().toLowerCase() : "";

                switch (letterType) {
                        case "promotion" -> {
                                addCenteredTitle(doc, "PROMOTION LETTER", titleFont);
                                doc.add(new Paragraph("Date: " + emp.getEffectiveDate(), bodyFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph("To,", bodyFont));
                                doc.add(new Paragraph(emp.getEmployeeName(), boldFont));
                                doc.add(new Paragraph(emp.getDepartment() + " Department", bodyFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph("Subject: Promotion Confirmation", boldFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph("Dear " + emp.getEmployeeName() + ",", bodyFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph(
                                                "We are pleased to inform you that in recognition of your hard work and dedication since your joining on "
                                                                + emp.getDoj()
                                                                + ", you are being promoted to the position of "
                                                                + emp.getNewDesignation() + " in the "
                                                                + emp.getDepartment() + " department.",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph(
                                                "This promotion will be effective from " + emp.getEffectiveDate() + ".",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph(
                                                "Please accept our heartfelt congratulations on this well-deserved recognition of your efforts.",
                                                bodyFont));
                                doc.add(new Paragraph(
                                                "We look forward to your continued contributions and success in your new role.",
                                                bodyFont));
                        }

                        case "appraisal" -> {
                                addCenteredTitle(doc, "company (INDIA) LIMITED", titleFont);

                                Font confidentialFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11,
                                                Font.ITALIC, BaseColor.RED);
                                Paragraph confidentialPara = new Paragraph("Strictly Confidential", confidentialFont);
                                confidentialPara.setAlignment(Element.ALIGN_LEFT);
                                doc.add(confidentialPara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph codePara = new Paragraph();
                                codePara.add(new Chunk("Employee Code - ", bodyFont));
                                codePara.add(new Chunk(emp.getEmpId(), boldFont));
                                doc.add(codePara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph salutation = new Paragraph();
                                salutation.add(new Chunk("Dear ", bodyFont));
                                salutation.add(new Chunk(emp.getEmployeeName(), boldFont));
                                salutation.add(new Chunk(",", bodyFont));
                                doc.add(salutation);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "On behalf of the organization, we would like to thank you for your immense contribution to the organisation’s growth.",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);

                                Paragraph ratingPara = new Paragraph();
                                ratingPara.add(new Chunk(
                                                "We are pleased to inform you that your performance rating for the financial year 2024-25 has been rated  ",
                                                bodyFont));
                                ratingPara.add(new Chunk(emp.getPmsRating(), boldFont));
                                doc.add(ratingPara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph ctcPara = new Paragraph();
                                ctcPara.add(new Chunk(
                                                "For your performance of FY 2024-25, the management is pleased to revise your compensation to INR  ",
                                                bodyFont));
                                double ctcInLakhs = Double.parseDouble(emp.getNewCtc());
                                long ctcInRupees = Math.round(ctcInLakhs * 100000);
                                String formattedCtc = "₹" + ctcInRupees + "/-"; // ✅ No commas
                                ctcPara.add(new Chunk(formattedCtc, boldFont));

                                ctcPara.add(new Chunk(
                                                " p.a with effect from April 01, 2025. All other terms of your appointment shall remain unchanged.",
                                                bodyFont));
                                doc.add(ctcPara);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "Thank you once again for your dedication and hard work. We are confident that you will continue to add value and take the company (India) Limited to greater heights in the future.",
                                                bodyFont));
                        }

                        case "no_increment" -> {
                                addCenteredTitle(doc, "company (INDIA) LIMITED", titleFont);

                                Font confidentialFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11,
                                                Font.ITALIC, BaseColor.RED);
                                Paragraph confidentialPara = new Paragraph("Strictly Confidential", confidentialFont);
                                confidentialPara.setAlignment(Element.ALIGN_LEFT);
                                doc.add(confidentialPara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph codePara = new Paragraph();
                                codePara.add(new Chunk("Employee Code - ", bodyFont));
                                codePara.add(new Chunk(emp.getEmpId(), boldFont));
                                doc.add(codePara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph salutation = new Paragraph();
                                salutation.add(new Chunk("Dear ", bodyFont));
                                salutation.add(new Chunk(emp.getEmployeeName(), boldFont));
                                salutation.add(new Chunk(",", bodyFont));
                                doc.add(salutation);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "On behalf of the organization, we would like to express our appreciation for your efforts over the past year.",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);

                                Paragraph ratingPara = new Paragraph();
                                ratingPara.add(new Chunk(
                                                "After careful consideration, your performance rating for the financial year 2024-25 has been rated ",
                                                bodyFont));
                                ratingPara.add(new Chunk(emp.getPmsRating(), boldFont));
                                ratingPara.add(new Chunk(
                                                ". Based on this evaluation, there will be no increment to your current salary at this time.",
                                                bodyFont));
                                doc.add(ratingPara);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "We believe in your potential and are initiating a Performance Improvement Program (PIP) to assist you in improving your performance. This program is an opportunity for professional development, and we encourage you to take full advantage of the support and resources provided.",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);

                                Paragraph emailPara = new Paragraph();
                                emailPara.add(new Chunk("For any queries, please feel free to get in touch with ",
                                                bodyFont));
                                Anchor emailLink = new Anchor("learning@companygroup.com", linkFont);
                                emailLink.setReference("mailto:learning@companygroup.com");
                                emailPara.add(emailLink);
                                doc.add(emailPara);

                                doc.add(Chunk.NEWLINE);
                                doc.add(new Paragraph(
                                                "We look forward to your progress and contributions in the coming year.",
                                                bodyFont));
                        }

                        case "partial_increment" -> {
                                addCenteredTitle(doc, "company (INDIA) LIMITED", titleFont);

                                Font confidentialFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11,
                                                Font.ITALIC, BaseColor.RED);
                                Paragraph confidentialPara = new Paragraph("Strictly Confidential", confidentialFont);
                                confidentialPara.setAlignment(Element.ALIGN_LEFT);
                                doc.add(confidentialPara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph codePara = new Paragraph();
                                codePara.add(new Chunk("Employee Code - ", bodyFont));
                                codePara.add(new Chunk(emp.getEmpId(), boldFont));
                                doc.add(codePara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph salutation = new Paragraph();
                                salutation.add(new Chunk("Dear ", bodyFont));
                                salutation.add(new Chunk(emp.getEmployeeName(), boldFont));
                                salutation.add(new Chunk(",", bodyFont));
                                doc.add(salutation);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "On behalf of the organization, we would like to express our appreciation for your efforts over the past year.",
                                                bodyFont));
                                doc.add(Chunk.NEWLINE);

                                Paragraph ratingPara = new Paragraph();
                                ratingPara.add(new Chunk(
                                                "After careful consideration, your performance rating for the financial year 2023-24 has been evaluated as ",
                                                bodyFont));
                                ratingPara.add(new Chunk(emp.getPmsRating(), boldFont));
                                doc.add(ratingPara);
                                doc.add(Chunk.NEWLINE);

                                Paragraph ctcPara = new Paragraph();
                                ctcPara.add(new Chunk(
                                                "However, the management has decided to revise your compensation to INR ",
                                                bodyFont));
                                double ctcInLakhs = Double.parseDouble(emp.getNewCtc());
                                long ctcInRupees = Math.round(ctcInLakhs * 100000);
                                String formattedCtc = "₹" + ctcInRupees + "/-"; // ✅ No commas
                                ctcPara.add(new Chunk(formattedCtc, boldFont));

                                ctcPara.add(new Chunk(
                                                " p.a with effect from April 01, 2025. All other terms of your appointment shall remain unchanged.",
                                                bodyFont));
                                doc.add(ctcPara);
                                doc.add(Chunk.NEWLINE);

                                doc.add(new Paragraph(
                                                "We believe in your potential and are initiating a Performance Improvement Program (PIP) to assist you to improve your performance. This program is an opportunity for professional development, and we encourage you to take full advantage of the support and resources provided.",
                                                bodyFont));
                        }

                        default -> {
                                Paragraph unknown = new Paragraph("⚠ UNKNOWN LETTER TYPE: " + letterType, titleFont);
                                unknown.setAlignment(Element.ALIGN_CENTER);
                                doc.add(unknown);
                        }
                }

                // Closing
                doc.add(Chunk.NEWLINE);
                doc.add(new Paragraph("Best Regards,", bodyFont));
                doc.add(new Paragraph("Human Resources", boldFont));
                doc.add(Chunk.NEWLINE);

                // Footer 1: Disclaimer after signature
                doc.add(Chunk.NEWLINE);
                Font smallItalicFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);
                doc.add(new Paragraph(
                                "This is an electronically generated document; hence signature is not required. "
                                                + "Please note that Employee CTC and other salary details are very personalized and confidential. "
                                                + "Discussing salary details with others within the organization or outside is not advisable and doing so may lead to disciplinary action.",
                                smallItalicFont));

                doc.close();
                return out.toByteArray();
        }

        private void addCenteredTitle(Document doc, String text, Font font) throws DocumentException {
                Paragraph title = new Paragraph(text, font);
                title.setAlignment(Element.ALIGN_CENTER);
                doc.add(title);
                doc.add(Chunk.NEWLINE);
        }
}
