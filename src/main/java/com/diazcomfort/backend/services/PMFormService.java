package com.diazcomfort.backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.diazcomfort.backend.DTOs.PMFormCreateDTO;
import com.diazcomfort.backend.DTOs.PMFormEditDTO;
import com.diazcomfort.backend.DTOs.PMFormListEditDTO;
import com.diazcomfort.backend.entity.PMForm;
import com.diazcomfort.backend.helpers.Mapper;
import com.diazcomfort.backend.repository.PMFormRepository;
import com.diazcomfort.backend.services.interfaces.IPMFormService;

@Service
public class PMFormService implements IPMFormService{

    private final PMFormRepository pmFormRepository;
    private final Mapper mapper;

    public PMFormService(PMFormRepository pmFormRepository,Mapper mapper) {
        this.pmFormRepository = pmFormRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PMForm> guardPmForm(List<PMFormCreateDTO> pmFormCreateDTO) {
        return pmFormRepository.saveAll(mapper.convertPMFormCreateDTOList(pmFormCreateDTO));

    }

    @Override
    public List<PMFormEditDTO> getPMFormsByPMCheckId(UUID PMCheckId) {
        List<PMForm> pmForms = pmFormRepository.findByPmcheckPMCheckId(PMCheckId);
        List<PMFormEditDTO> pmFormListEditDTOs = mapper.PMFormToPMFormListEditDTO(pmForms);
        return pmFormListEditDTOs;
    }

    @Override
    public List<PMFormEditDTO> editPMForms(PMFormListEditDTO pmFormListEditDTO) {
        List<PMForm> pmFormListEditDTOs = pmFormRepository.saveAll(mapper.listPMFormEditDTOtoListPMForm(pmFormListEditDTO.getListDTO()));
        return mapper.PMFormToPMFormListEditDTO(pmFormListEditDTOs);
    }
    
}
