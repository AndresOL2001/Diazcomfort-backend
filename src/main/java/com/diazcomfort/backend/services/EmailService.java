package com.diazcomfort.backend.services;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.helpers.PMCheckServiceHandler;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailAttachment;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

@Service
public class EmailService {

    private final PMCheckServiceHandler pmCheckServiceHandler;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Value("${access_key}")
    private String access_key;

    public EmailService(PMCheckServiceHandler pmCheckServiceHandler) {
        this.pmCheckServiceHandler = pmCheckServiceHandler;
    }

    public void sendEmailWithAttachment(File pdf, String id) {
        Optional<PMCheck> pmCheck = pmCheckServiceHandler.verifyExistence(UUID.fromString(id));
        if (pmCheck.isEmpty()) {
            logger.error("PDFService - The PMCheck with that id doesnt exists");
            throw new RuntimeException("The PMCheck with that id doesnt exists");
        }
        try {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKey.setApiKey(access_key);

            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
            Map<String, String> params = new HashMap<String, String>();
            params.put("customer", pmCheck.get().getCustomer());
            params.put("reviewedby", pmCheck.get().getUsuario().getName());

            SendSmtpEmailTo sendSmtpEmailTo = getSmtptEmailTo("andresolaje@gmail.com");

            SendSmtpEmailSender sendSmtpEmailSender = getSendSmtpEmailSender();

            /*
             * byte[] bFile =
             * readBytesFromFile("/home/Testing123/Downloads/Testing123.jpg");
             * byte[] bArr =Base64.getEncoder().encodeToString(bFile).getBytes();
             */

            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            LocalDate localDate = LocalDate.now();

            SendSmtpEmailAttachment sendSmtpEmailAttachment1 = new SendSmtpEmailAttachment();

            sendSmtpEmailAttachment1.setName("PM_CHECK - " + localDate + " - " + pmCheck.get().getUsuario().getName() + ".pdf");

            byte[] fileContent = Files.readAllBytes(pdf.toPath());

            sendSmtpEmailAttachment1.setContent(fileContent);

            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();

            attachmentList.add(sendSmtpEmailAttachment1);

            sendSmtpEmail.setAttachment(attachmentList);
            sendSmtpEmail.sender(sendSmtpEmailSender);
            sendSmtpEmail.to(Arrays.asList(sendSmtpEmailTo));
            /*
             * sendSmtpEmail.
             * setHtmlContent("<html><body><h1>This is my first transactional email {{params.parameter}}</h1></body></html>"
             * );
             */
            sendSmtpEmail.params(params);
            sendSmtpEmail.templateId(1L);

            // sendSmtpEmail.setAttachment(Arrays.asList(sendSmtpEmailAttachment1));

            /*
             * System.out.println(sendSmtpEmail);
             */
            CreateSmtpEmail response = apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());

        } catch (ApiException e) {
            System.err.println("Exception when calling SmtpApi#sendTransacEmail");

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SendSmtpEmailSender getSendSmtpEmailSender() {
        SendSmtpEmailSender sendSmtpEmailSender = new SendSmtpEmailSender();
        sendSmtpEmailSender.setEmail("diazcomfortairsystem@gmail.com");
        sendSmtpEmailSender.setName("Diaz Comfort Air System");
        return sendSmtpEmailSender;
    }

    public SendSmtpEmailTo getSmtptEmailTo(String correo) {
        SendSmtpEmailTo sendSmtpEmailTo = new SendSmtpEmailTo();
        sendSmtpEmailTo.setEmail(correo);
        sendSmtpEmailTo.setName("Andrés Olaje");
        return sendSmtpEmailTo;
    }
}
