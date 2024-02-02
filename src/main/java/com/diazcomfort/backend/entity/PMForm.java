package com.diazcomfort.backend.entity;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.diazcomfort.backend.entity.valueobjects.PMStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "diazcomfort")
public class PMForm {
    @Id
    public UUID PMFormId;

    //#1
    public boolean checkPowerSystem;
    //#2
    public boolean checkMotors;
    //#3
    public boolean checkCapacitors;
    //#4
    public boolean inspectContactor;
    //#5
    public boolean inspectEvapCoil;
    //#6
    public boolean powerUnitOn;
    //#7
    public boolean turnSystemCoolMode;
    //#8
    public boolean checkRefrigerant;
    //#9
    public boolean cleanCondensateCoil;
    //#10
    public boolean cleanCondensatePipe;
    //#11 -> renamed to Inspect High And Low Voltage Wiring
    public boolean takePictures;
    //#12
    public boolean unitOperationStatus;

    public boolean checkBelts;
    public boolean checkDuctWork;
    public boolean checkThermostat;
    public boolean checkAmps;
    public boolean splitTemperature;
    public boolean airFilters;
    public boolean checkFuses;

    //RATINGS
    @Enumerated(EnumType.STRING)
    public PMStatus checkPowerSystemRate;
    //#2
    @Enumerated(EnumType.STRING)
    public PMStatus checkMotorsRate;
    //#3
    @Enumerated(EnumType.STRING)
    public PMStatus checkCapacitorsRate;
    //#4
    @Enumerated(EnumType.STRING)
    public PMStatus inspectContactorRate;
    //#5
    @Enumerated(EnumType.STRING)
    public PMStatus inspectEvapCoilRate;
    //#6
    @Enumerated(EnumType.STRING)
    public PMStatus powerUnitOnRate;
    //#7
    @Enumerated(EnumType.STRING)
    public PMStatus turnSystemCoolModeRate;
    //#8
    @Enumerated(EnumType.STRING)
    public PMStatus checkRefrigerantRate;
    //#9
    @Enumerated(EnumType.STRING)
    public PMStatus cleanCondensateCoilRate;
    //#10
    @Enumerated(EnumType.STRING)
    public PMStatus cleanCondensatePipeRate;
    //#11 -> renamed to Inspect High And Low Voltage Wiring
    @Enumerated(EnumType.STRING)
    public PMStatus takePicturesRate;
    //#12
    @Enumerated(EnumType.STRING)
    public PMStatus unitOperationStatusRate;

    @Enumerated(EnumType.STRING)
    public PMStatus checkBeltsRate;
    @Enumerated(EnumType.STRING)
    public PMStatus checkDuctWorkRate;
    @Enumerated(EnumType.STRING)
    public PMStatus checkThermostatRate;

    @Enumerated(EnumType.STRING)
    public PMStatus checkAmpsRate;

    @Enumerated(EnumType.STRING)
    public PMStatus splitTemperatureRate;

    @Enumerated(EnumType.STRING)
    public PMStatus airFiltersRate;

    @Enumerated(EnumType.STRING)
    public PMStatus checkFusesRate;
    //EQUIPMENT FORM
    private String tonnage;

    private String type;

    private String age;

    private String brand;

    private String notes;

    @OneToMany(mappedBy = "form",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PMImage> images = new HashSet<>();

    //Actualizacion modelos
    public String modelFurnance;
    public String modelEvaporatorCoil;
    public String modelCondenser;
    public String modelPackageUnit;
    public String refrigerantType;

    @ManyToOne(fetch=FetchType.EAGER)//Si eliminamos el siniestro todas sus documentos igual se eliminan
    @JoinColumn(name = "id_pm_check")
    @JsonIgnore
    public PMCheck pmcheck;


    public PMForm(UUID id) {
        this.PMFormId = id;
    }

}
