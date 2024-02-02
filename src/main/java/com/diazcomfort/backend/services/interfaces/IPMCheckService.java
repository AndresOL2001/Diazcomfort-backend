package com.diazcomfort.backend.services.interfaces;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diazcomfort.backend.DTOs.PMCheckCreateDTO;
import com.diazcomfort.backend.DTOs.PMCheckEditDTO;
import com.diazcomfort.backend.entity.PMCheck;

public interface IPMCheckService {
    public PMCheck guardarPmCheck(PMCheckCreateDTO pmCheckCreateDTO);
    public List<PMCheck> obtneerPMChecks(int page);
    public Page<PMCheck> buscarChecks(String search,LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    public PMCheck obtenerPorId(String id);
    public PMCheck editarPMCheck(PMCheckEditDTO PMCheckEditDTO,String id);

    
}
