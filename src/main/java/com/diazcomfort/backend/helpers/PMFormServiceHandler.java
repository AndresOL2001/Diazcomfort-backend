package com.diazcomfort.backend.helpers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.diazcomfort.backend.entity.PMForm;
import com.diazcomfort.backend.repository.PMFormRepository;

@Component
public class PMFormServiceHandler {
    private final PMFormRepository pmFormRepository;
    public PMFormServiceHandler(PMFormRepository pmFormRepository) {
        this.pmFormRepository = pmFormRepository;
    }

    public Optional<PMForm> verifyExistence(UUID pmFormId){
        return pmFormRepository.findById(pmFormId);
    }
}
