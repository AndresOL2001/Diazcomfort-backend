package com.diazcomfort.backend.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diazcomfort.backend.DTOs.PMFormListCreateDTO;
import com.diazcomfort.backend.DTOs.PMFormListEditDTO;
import com.diazcomfort.backend.DTOs.PMFormEditDTO;
import com.diazcomfort.backend.entity.PMForm;
import com.diazcomfort.backend.services.PMFormService;

@RestController
@RequestMapping("/api/PMForm")
public class PMFormController {
    private final PMFormService pmFormService;
    private final Logger logger = LoggerFactory.getLogger(PMFormController.class);

    public PMFormController(PMFormService pmFormService) {
        this.pmFormService = pmFormService;
    }

    @PostMapping()
    public ResponseEntity<?> createPMForm(@RequestBody PMFormListCreateDTO pmFormCreateDTO) {
        try {
            System.out.println(pmFormCreateDTO.getListDTO().size());
            List<PMForm> pmForms = pmFormService.guardPmForm(pmFormCreateDTO.getListDTO());
            // Extract IDs from PMForms
             List<String> pmFormIds = pmForms.stream()
                    .map(pmForm -> pmForm.getPMFormId().toString())
                    .collect(Collectors.toList());

            // Return only the IDs in the response
            return new ResponseEntity<>(pmFormIds, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMFormController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{pmcheckId}")
    public ResponseEntity<?> getAllPmFormsByPmCheckId(@PathVariable String pmcheckId) {
        try {
            List<PMFormEditDTO> pmForms = pmFormService.getPMFormsByPMCheckId(UUID.fromString(pmcheckId));

            return new ResponseEntity<>(pmForms, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMFormController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> editPMForms(@RequestBody PMFormListEditDTO pmFormListEditDTO){
        try {
            List<PMFormEditDTO> pmForms = pmFormService.editPMForms(pmFormListEditDTO);

            return new ResponseEntity<>(pmForms, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMFormController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
