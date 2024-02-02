package com.diazcomfort.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.diazcomfort.backend.DTOs.PMCheckCreateDTO;
import com.diazcomfort.backend.DTOs.PMCheckEditDTO;
import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.helpers.Mapper;
import com.diazcomfort.backend.repository.PMCheckRepository;
import com.diazcomfort.backend.services.interfaces.IPMCheckService;

@Service
public class PMCheckService implements IPMCheckService {
    private final PMCheckRepository pmCheckRepository;
    private final Mapper mapper;

    public PMCheckService(PMCheckRepository pmCheckRepository, Mapper mapper) {
        this.pmCheckRepository = pmCheckRepository;
        this.mapper = mapper;
    }

    @Override
    public PMCheck guardarPmCheck(PMCheckCreateDTO pmCheckCreateDTO) {
        return pmCheckRepository.save(mapper.PMCheckCreateDTOtoPMCheck(pmCheckCreateDTO));
    }

    @Override
    public List<PMCheck> obtneerPMChecks(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        Slice<PMCheck> slice = pmCheckRepository.findAll(pageable);
        return slice.getContent();
    }

    @Override
    public Page<PMCheck> buscarChecks(String search,LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

        if (startDate != null && endDate != null) {
            // Use the method for date range filtering
            return pmCheckRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        } else {
            // Use the existing method for other criteria
            return pmCheckRepository.findByCustomerContainingIgnoreCaseOrUsuarioNameContainingIgnoreCaseOrInspectionFeeContainingOrRecomendationTotalPriceContaining(
            search, search, search, search,pageable
        );
        }
       
    }

    @Override
    public PMCheck obtenerPorId(String id) {
        Optional<PMCheck> PMCheckO= pmCheckRepository.findById(UUID.fromString(id));
        if(PMCheckO.isEmpty()){
            throw new RuntimeException("Error: id inexistente");
        }

        return PMCheckO.get();
    }

    @Override
    public PMCheck editarPMCheck(PMCheckEditDTO PMCheckEditDTO,String id) {
        Optional<PMCheck> PMCheckO= pmCheckRepository.findById(UUID.fromString(id));
        if(PMCheckO.isEmpty()){
            throw new RuntimeException("Error: id inexistente");
        }
        PMCheck pmCheckDB = mapper.PMCheckEditDTOtoPMCheck(PMCheckEditDTO);
        pmCheckDB.setCreatedAt(PMCheckO.get().getCreatedAt());
        pmCheckDB.setUsuario(PMCheckO.get().getUsuario());
        return pmCheckRepository.save(pmCheckDB);

    }

}
