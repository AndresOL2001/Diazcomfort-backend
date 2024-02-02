package com.diazcomfort.backend.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diazcomfort.backend.DTOs.PMCheckCreateDTO;
import com.diazcomfort.backend.DTOs.PMCheckEditDTO;
import com.diazcomfort.backend.DTOs.PMCheckResponseDTO;
import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.services.PMCheckService;

@RestController
@RequestMapping("/api/PMCheck")
public class PMCheckController {
    private final PMCheckService pmCheckService;
    private final Logger logger = LoggerFactory.getLogger(PMCheckController.class);

    public PMCheckController(PMCheckService pmCheckService) {
        this.pmCheckService = pmCheckService;
    }

    @PostMapping()
    public ResponseEntity<?> createPMCheck(@RequestBody PMCheckCreateDTO pmCheckCreateDTO) {
        try {
            PMCheck pmCheck = pmCheckService.guardarPmCheck(pmCheckCreateDTO);
            return new ResponseEntity<>(new PMCheckResponseDTO(pmCheck.getPMCheckId().toString()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMCheckController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            PMCheck pmCheck = pmCheckService.obtenerPorId(id);
            return new ResponseEntity<>(pmCheck, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMCheckController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPMChecks(@RequestParam Optional<String> search,
    Pageable page, 
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        if(search.isEmpty()){
            return new ResponseEntity<>(pmCheckService.buscarChecks("", startDate,endDate,page),HttpStatus.OK);
        }

        return new ResponseEntity<>(pmCheckService.buscarChecks(search.get(),startDate,endDate, page), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPMCheck(@RequestBody PMCheckEditDTO pmCheckEditDTO,@PathVariable String id){
        try {
            PMCheck pmCheck = pmCheckService.editarPMCheck(pmCheckEditDTO,id);
            return new ResponseEntity<>(pmCheck, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("PMCheckController - Error:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
