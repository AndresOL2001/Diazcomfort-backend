package com.diazcomfort.backend.DTOs;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PMCheckEditDTO {

    private String customer;

    private String inspectionFee;

    private String recomendationTotalPrice;

    private String pmcheckId;

    private String address;
}
