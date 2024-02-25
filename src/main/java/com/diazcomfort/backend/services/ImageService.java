package com.diazcomfort.backend.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diazcomfort.backend.entity.PMForm;
import com.diazcomfort.backend.entity.PMImage;
import com.diazcomfort.backend.repository.ImageRepository;
import com.diazcomfort.backend.services.interfaces.IImageService;
import com.diazcomfort.backend.helpers.PMFormServiceHandler;
import com.diazcomfort.backend.helpers.Utils;

@Service
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final PMFormServiceHandler pmFormServiceHandler;
    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final String folder = "/opt/images/";
    private final Utils utils;
    // private final String folder = "C:/images/";

    public ImageService(ImageRepository imageRepository, PMFormServiceHandler pmFormServiceHandler, Utils utils) {
        this.imageRepository = imageRepository;
        this.pmFormServiceHandler = pmFormServiceHandler;
        this.utils = utils;
    }

    @Override
    public PMImage guardarImagen(MultipartFile imagen, String pmcheckId) {
        logger.info("Image Service - Guardando imagen");
        UUID uuid = UUID.randomUUID();
        String extension = "-" + imagen.getOriginalFilename();
        Optional<PMForm> PMForm = pmFormServiceHandler.verifyExistence(UUID.fromString(pmcheckId));
        if (!PMForm.isPresent()) {
            throw new RuntimeException("PMForm with that id doesn´t exist");
        }
        String currentUrl = getImageURL(uuid, extension); // Implement this method based on your

        PMImage diplomadoNew = imageRepository.save(new PMImage(uuid, currentUrl, PMForm.get()));
        byte[] bytes;
        try {
            bytes = imagen.getBytes();
            String formatName = utils.obtenerFormato(imagen.getContentType());
            bytes = utils.compressImage(bytes, formatName);
            // Print size after compression
            Path path = Paths.get(folder + uuid + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            logger.error("ImageService", e.getMessage());
            e.printStackTrace();
        }
        return diplomadoNew;
    }

    @Override
    public void deleteImage(UUID PMImageId) {
        Optional<PMImage> PMImage = imageRepository.findById(PMImageId);
        if (!PMImage.isPresent()) {
            logger.error("ImageService PMImage with that id doesn´t exist");
            throw new RuntimeException("PMImage with that id doesn´t exist");
        }
        imageRepository.deleteById(PMImageId);
        File file = new File(folder + PMImage.get().getUrl().split("/")[4]);
        if (file.delete()) {
            logger.info(file.getName() + " is deleted!");
        } else {
            logger.info("Delete Operation failed");
        }
    }

    private String getImageURL(UUID uuid, String extension) {
        return "http://azdcair.com/images/" + uuid + extension;
    }

}
