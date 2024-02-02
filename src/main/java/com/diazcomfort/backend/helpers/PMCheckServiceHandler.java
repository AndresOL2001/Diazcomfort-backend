package com.diazcomfort.backend.helpers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.repository.PMCheckRepository;

@Component
public class PMCheckServiceHandler {
    
    private final PMCheckRepository pmCheckRepository;
    public PMCheckServiceHandler(PMCheckRepository pmCheckRepository) {
        this.pmCheckRepository = pmCheckRepository;
    }

    public Optional<PMCheck> verifyExistence(UUID pmcheckId){
        return pmCheckRepository.findById(pmcheckId);
    }


}
