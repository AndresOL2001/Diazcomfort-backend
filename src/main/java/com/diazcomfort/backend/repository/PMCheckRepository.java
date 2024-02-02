package com.diazcomfort.backend.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diazcomfort.backend.entity.PMCheck;

@Repository
public interface PMCheckRepository extends JpaRepository<PMCheck,UUID>{

    Page<PMCheck> findByCustomerContainingIgnoreCaseOrUsuarioNameContainingIgnoreCaseOrInspectionFeeContainingOrRecomendationTotalPriceContaining(
        String customer, String technician, String inspectionFee, String recomendationTotalPrice, Pageable pageable
    );
    
    Page<PMCheck> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}
