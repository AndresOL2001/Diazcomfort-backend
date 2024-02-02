package com.diazcomfort.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.diazcomfort.backend.DTOs.PMFormCreateDTO;
import com.diazcomfort.backend.DTOs.PMFormEditDTO;
import com.diazcomfort.backend.DTOs.PMFormListEditDTO;
import com.diazcomfort.backend.entity.PMForm;

public interface IPMFormService {
        public List<PMForm> guardPmForm(List<PMFormCreateDTO> pmFormCreateDTO);

        public List<PMFormEditDTO> getPMFormsByPMCheckId(UUID PMCheckId);

        public List<PMFormEditDTO> editPMForms(PMFormListEditDTO pmFormListEditDTO);
}
