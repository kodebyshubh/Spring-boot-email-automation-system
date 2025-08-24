package com.example.emailsystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class FooterPageEvent extends PdfPageEventHelper {
    private final Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY); // 👈 light
                                                                                                         // gray font

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();

        float xStart = document.left();
        float xEnd = document.right();
        float y = document.bottom() + 30;

        // Draw a light gray horizontal line
        canvas.setColorStroke(BaseColor.LIGHT_GRAY); // 👈 lighter line
        canvas.setLineWidth(0.5f);
        canvas.moveTo(xStart, y);
        canvas.lineTo(xEnd, y);
        canvas.stroke();

        // Footer text in light gray
        Phrase footer = new Phrase(
                "Registered Office : company adress", footerFont);
        ColumnText.showTextAligned(canvas,
                Element.ALIGN_CENTER,
                footer,
                (document.left() + document.right()) / 2,
                document.bottom() + 20,
                0);
    }
}
