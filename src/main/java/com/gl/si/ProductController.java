package com.gl.si;

import com.gl.si.model.Product;
import com.gl.si.notifications.EmailService;
import com.gl.si.services.PdfGeneratorService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final PdfGeneratorService pdfGeneratorService;
    private final EmailService emailService;

    public ProductController(PdfGeneratorService pdfGeneratorService, EmailService emailService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.emailService = emailService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getProductsPdf() throws IOException {
        byte[] pdfBytes = pdfGeneratorService.generatePdf(getSampleProducts());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "products.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @GetMapping("/send-email")
    public String sendRapport() throws IOException, MessagingException {
        byte[] pdfBytes = pdfGeneratorService.generatePdf(getSampleProducts());
        emailService.sendRapportPdf("seyeadam28@gmail.com", "Rapport produits", "Voici le rapport des produits", pdfBytes);
        return "Email sent successfully";
    }

    private List<Product> getSampleProducts() {
        return List.of(
                new Product("description apple", "Iphone 14", 300, 8),
                new Product("description samsung", "Samsung Galaxy S21", 400, 9),
                new Product("description frigo", "Frigo 120L", 150, 10),
                new Product("description ordinateur apple", "MacBook Pro", 800, 5),
                new Product("description lave linge", "Lave linge 1200W", 100, 8)
        );
    }
}
