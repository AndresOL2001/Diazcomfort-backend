package com.diazcomfort.backend.helpers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String obtenerFormato(String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return "jpg";
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
            default:
                return "jpg"; // default to jpg if format is not recognized
        }
    }

    public byte[] compressImage(byte[] imageData, String formatName) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));

        // Create a ByteArrayOutputStream to write the compressed image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Write the compressed image to the ByteArrayOutputStream
        ImageIO.write(image, formatName, baos);

        return baos.toByteArray();
    }

}
