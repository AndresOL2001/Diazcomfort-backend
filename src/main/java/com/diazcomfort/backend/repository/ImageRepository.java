package com.diazcomfort.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diazcomfort.backend.entity.PMImage;

@Repository
public interface ImageRepository extends JpaRepository<PMImage,UUID>{

    
    
}
