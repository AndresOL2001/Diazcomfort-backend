package com.diazcomfort.backend.controllers;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.diazcomfort.backend.DTOs.MessageDTO;
import com.diazcomfort.backend.services.ImageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/api/files")
public class ImageController {

    private final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    public ResponseEntity<?> createPMImage(@RequestParam("files") MultipartFile[] files,
            @RequestParam("pmform") String id) {
        try {
            log.info("IMAGE CONTROLLER 36",files.length);
            Arrays.asList(files).stream().forEach(file -> {
                imageService.guardarImagen(file, id);
            });
            MessageDTO messageDTO = new MessageDTO("OK!:", "200");
            return new ResponseEntity<>(messageDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("IMAGE CONTROLLER 43",e.getMessage());
            MessageDTO messageDTO = new MessageDTO("Error: " + e.getMessage(), "500");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePMImage(@PathVariable String id) {
        try {
            imageService.deleteImage(UUID.fromString(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            MessageDTO messageDTO = new MessageDTO("Error: " + e.getMessage(), "500");
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
    }

}
