package com.diazcomfort.backend.DTOs;





import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PMCheckCreateDTO {

    public String customer;
    public String userId;

    public String inspectionFee;
    public String recomendationTotalPrice;
    public String address;

    public List<UUID> listPmIds;

}
