package com.gl.si.services;

import com.gl.si.model.Product;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class PdfGeneratorService {
    public byte[] generatePdf(List<Product> products) throws IOException {
        log.info("Generating PDF");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterEventHanlder());

        Paragraph title = new Paragraph("Liste des produits")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(title);

        document.add(new Paragraph("\n"));

        String textDesc= """
                Le soleil se levait lentement derrière les collines embrumées.
                Un vent léger faisait danser les feuilles encore couvertes de rosée.
                Au loin, on distinguait le chant discret d’un ruisseau sinueux.
                La journée promettait déjà une sérénité pleine de promesses.
                """;
        Paragraph description = new Paragraph(textDesc)
                .setFontSize(10)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(description);

        document.add(new Paragraph("\n"));

        Table table = new Table(5);
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell(new Cell().add(new Paragraph("Matricule")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Description")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Nom")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Prix")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantité")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        products.forEach(product->{
            table.addCell(new Cell().add(new Paragraph(product.getMatricule())));
            table.addCell(new Cell().add(new Paragraph(product.getDescription())));
            table.addCell(new Cell().add(new Paragraph(product.getName())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getPrice()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getQuantity()))));
        });
        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    private static class HeaderFooterEventHanlder implements IEventHandler{

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            Document doc = new Document(docEvent.getDocument());
            float y = doc.getBottomMargin() -20;

            Paragraph footer = new Paragraph("footer liste pdf")
                    .setFontSize(9)
                    .setFontColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER);

            doc.showTextAligned(footer,
                    297.5f, // milieu de la page A4 (595 / 2)
                    y,
                    docEvent.getDocument().getNumberOfPages(),
                    TextAlignment.CENTER,
                    null,
                    0);

        }
    }
}
