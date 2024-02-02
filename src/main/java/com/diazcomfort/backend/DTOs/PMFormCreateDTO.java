package com.diazcomfort.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PMFormCreateDTO {

    public String pmcheckid;
    public boolean checkPowerSystem;
    public boolean checkMotors;
    public boolean checkCapacitors;
    public boolean inspectContactor;
    public boolean inspectEvapCoil;
    public boolean powerUnitOn;
    public boolean turnSystemCoolMode;
    public boolean checkRefrigerant;
    public boolean cleanCondensateCoil;
    public boolean cleanCondensatePipe;
    public boolean takePictures;
    public boolean unitOperationStatus;

    public boolean checkBelts;
    public boolean checkDuctWork;
    public boolean checkThermostat;
    public boolean checkAmps;
    public boolean splitTemperature;
    public boolean airFilters;
    public boolean checkFuses;
    //RATINGS
    public String checkPowerSystemRate;
    public String checkMotorsRate;
    public String checkCapacitorsRate;
    public String inspectContactorRate;
    public String inspectEvapCoilRate;
    public String powerUnitOnRate;
    public String turnSystemCoolModeRate;
    public String checkRefrigerantRate;
    public String cleanCondensateCoilRate;
    public String cleanCondensatePipeRate;
    public String takePicturesRate;
    public String unitOperationStatusRate;

    public String checkBeltsRate;
    public String checkDuctWorkRate;
    public String checkThermostatRate;
    public String checkAmpsRate;
    public String splitTemperatureRate;
    public String airFiltersRate;
    public String checkFusesRate;
    //EQUIPMENT
    public String tonnage;
    public String type;
    public String age;
    public String brand;
    public String notes;

    //MODELS
    public String modelFurnance;
    public String modelEvaporatorCoil;
    public String modelCondenser;
    public String modelPackageUnit;
    public String refrigerantType;
    

}
