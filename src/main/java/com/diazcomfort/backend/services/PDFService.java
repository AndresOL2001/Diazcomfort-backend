package com.diazcomfort.backend.services;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.helpers.PMCheckServiceHandler;
import com.diazcomfort.backend.repository.UserRepository;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

import org.w3c.dom.Document;

@Service
public class PDFService {

    private static final String PDF_RESOURCES = "/pdf/";
    private static final Logger logger = LoggerFactory.getLogger(PDFService.class);

    private final SpringTemplateEngine springTemplateEngine;
    private final PMCheckServiceHandler pmCheckServiceHandler;

    @Autowired
    public PDFService(SpringTemplateEngine springTemplateEngine, UserRepository userRepository,
            PMCheckServiceHandler pmCheckServiceHandler) {
        this.springTemplateEngine = springTemplateEngine;
        this.pmCheckServiceHandler = pmCheckServiceHandler;
    }

    public File generarFacturasPDF(String id) throws Exception {
        Context context = generarContextoFacturasPDF(id);
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhtml(html);
        return renderearFacturasPDF(xhtml);
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);
        return out.toString();
    }

    private File renderearFacturasPDF(String html) throws Exception {
        File file = File.createTempFile("PMCheck", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context generarContextoFacturasPDF(String id) {

        Optional<PMCheck> pmCheck = pmCheckServiceHandler.verifyExistence(UUID.fromString(id));
        if (pmCheck.isEmpty()) {
            logger.error("PDFService - The PMCheck with that id doesnt exists");
            throw new RuntimeException("The PMCheck with that id doesnt exists");
        }
        //var user = this.userRepository.findById(pmCheck.get().getUsuario().getIdUser());
        Context context = new Context();
      /*   for (PMImage iterable_element : pmCheck.get().getImages()) {
            System.out.println(iterable_element.getUrl());
        } */
        context.setVariable("user", pmCheck.get().getUsuario().getName());
        context.setVariable("pmCheck", pmCheck.get());
        context.setVariable("pmForms", pmCheck.get().getPmforms());
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("FacturaPDF", context);
    }

}
