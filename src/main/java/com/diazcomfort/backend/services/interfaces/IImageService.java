package com.diazcomfort.backend.services.interfaces;


import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.diazcomfort.backend.entity.PMImage;

public interface IImageService {

    public PMImage guardarImagen(MultipartFile imagen, String pmcheckId);
    public void deleteImage(UUID PMImageId);

}
