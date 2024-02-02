package com.diazcomfort.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diazcomfort.backend.entity.PMForm;

@Repository
public interface PMFormRepository extends JpaRepository<PMForm,UUID>{
    List<PMForm> findByPmcheckPMCheckId(UUID pmCheckId);

}
