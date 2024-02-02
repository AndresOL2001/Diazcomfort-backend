package com.diazcomfort.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diazcomfort.backend.services.EmailService;
import com.diazcomfort.backend.services.PDFService;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    private final PDFService pdfService;
    private final EmailService emailService;

    public PDFController(PDFService pdfService, EmailService emailService) {
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    @PostMapping("/generate/{id}")
    public void generatePdf(@PathVariable String id,
            HttpServletResponse response)
            throws IOException {
        try {
            File pdfFile = pdfService.generarFacturasPDF(id);
            emailService.sendEmailWithAttachment(pdfFile, id);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable String id) {
        try {
            File pdfFile = pdfService.generarFacturasPDF(id);

            // Read the PDF file content into a byte array
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "HVAC-Report");

            // Return the PDF file content as ResponseEntity
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
