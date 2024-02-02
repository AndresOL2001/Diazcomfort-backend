package com.diazcomfort.backend.helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.diazcomfort.backend.DTOs.PMCheckCreateDTO;
import com.diazcomfort.backend.DTOs.PMCheckEditDTO;
import com.diazcomfort.backend.DTOs.PMFormCreateDTO;
import com.diazcomfort.backend.DTOs.PMFormEditDTO;
import com.diazcomfort.backend.DTOs.PMImageDTO;
import com.diazcomfort.backend.DTOs.RegistroUsuarioDTO;
import com.diazcomfort.backend.entity.PMCheck;
import com.diazcomfort.backend.entity.PMForm;
import com.diazcomfort.backend.entity.PMImage;
import com.diazcomfort.backend.entity.Rol;
import com.diazcomfort.backend.entity.User;
import com.diazcomfort.backend.entity.valueobjects.PMStatus;

@Component
public class Mapper {

    private final PasswordEncoder passwordEncoder;

    public Mapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User loginUserDTOtoUser(RegistroUsuarioDTO usuario) {
        return User.builder()
                .password(passwordEncoder.encode(usuario.getPassword()))
                .name(usuario.getName())
                .idUser(UUID.randomUUID())
                .email(usuario.getEmail())
                .rol(new Rol(UUID.fromString("15e89841-f0dc-4eab-a5fa-f8c8e643e3ff")))
                .build();
    }

    public PMCheck PMCheckCreateDTOtoPMCheck(PMCheckCreateDTO pmCheckCreateDTO) {

        ZoneId arizonaTimeZone = ZoneId.of("America/Phoenix");

        return PMCheck.builder()
                .PMCheckId(UUID.randomUUID())
                .createdAt(LocalDateTime.now(arizonaTimeZone))
                .address(pmCheckCreateDTO.getAddress())
                .customer(pmCheckCreateDTO.getCustomer())
                .inspectionFee(pmCheckCreateDTO.getInspectionFee())
                .recomendationTotalPrice(pmCheckCreateDTO.getRecomendationTotalPrice())
                .updatedAt(LocalDateTime.now())
                .usuario(new User(UUID.fromString(pmCheckCreateDTO.getUserId())))
                .build();
    }

    public List<PMForm> convertPMFormCreateDTOList(List<PMFormCreateDTO> pmFormCreateDTOList) {
        return pmFormCreateDTOList.stream()
                .map(this::PMFormCreateDTOtoPMForm)
                .collect(Collectors.toList());
    }
    public PMForm PMFormCreateDTOtoPMForm(PMFormCreateDTO pmFormCreateDTO) {
        return PMForm.builder()
                .PMFormId(UUID.randomUUID())
                .pmcheck(new PMCheck(UUID.fromString(pmFormCreateDTO.getPmcheckid())))
                .age(pmFormCreateDTO.getAge())
                .brand(pmFormCreateDTO.getBrand())
                .checkCapacitors(pmFormCreateDTO.isCheckCapacitors())
                .checkCapacitorsRate(PMStatus.valueOf(pmFormCreateDTO.getCheckCapacitorsRate().toUpperCase()))
                .checkMotors(pmFormCreateDTO.isCheckMotors())
                .checkMotorsRate(PMStatus.valueOf(pmFormCreateDTO.getCheckMotorsRate().toUpperCase()))
                .checkPowerSystem(pmFormCreateDTO.isCheckPowerSystem())
                .checkPowerSystemRate(PMStatus.valueOf(pmFormCreateDTO.getCheckPowerSystemRate().toUpperCase()))
                .checkRefrigerant(pmFormCreateDTO.isCheckRefrigerant())
                .checkRefrigerantRate(PMStatus.valueOf(pmFormCreateDTO.getCheckRefrigerantRate().toUpperCase()))
                .cleanCondensateCoil(pmFormCreateDTO.isCleanCondensateCoil())
                .cleanCondensateCoilRate(PMStatus.valueOf(pmFormCreateDTO.getCleanCondensateCoilRate().toUpperCase()))
                .cleanCondensatePipe(pmFormCreateDTO.isCleanCondensatePipe())
                .cleanCondensatePipeRate(PMStatus.valueOf(pmFormCreateDTO.getCleanCondensatePipeRate().toUpperCase()))
                .inspectContactor(pmFormCreateDTO.isInspectContactor())
                .inspectContactorRate(PMStatus.valueOf(pmFormCreateDTO.getInspectContactorRate().toUpperCase()))
                .inspectEvapCoil(pmFormCreateDTO.isInspectEvapCoil())
                .inspectEvapCoilRate(PMStatus.valueOf(pmFormCreateDTO.getInspectEvapCoilRate().toUpperCase()))
                .modelCondenser(pmFormCreateDTO.getModelCondenser())
                .modelEvaporatorCoil(pmFormCreateDTO.getModelEvaporatorCoil())
                .modelFurnance(pmFormCreateDTO.getModelFurnance())
                .modelPackageUnit(pmFormCreateDTO.getModelPackageUnit())
                .refrigerantType(pmFormCreateDTO.getRefrigerantType())
                .notes(pmFormCreateDTO.getNotes())
                .powerUnitOn(pmFormCreateDTO.isPowerUnitOn())
                .powerUnitOnRate(PMStatus.valueOf(pmFormCreateDTO.getPowerUnitOnRate().toUpperCase()))
                .takePictures(pmFormCreateDTO.isTakePictures())
                .takePicturesRate(PMStatus.valueOf(pmFormCreateDTO.getTakePicturesRate().toUpperCase()))
                .tonnage(pmFormCreateDTO.getTonnage())
                .turnSystemCoolMode(pmFormCreateDTO.isTurnSystemCoolMode())
                .turnSystemCoolModeRate(PMStatus.valueOf(pmFormCreateDTO.getTurnSystemCoolModeRate().toUpperCase()))
                .type(pmFormCreateDTO.getType())
                .unitOperationStatus(pmFormCreateDTO.isUnitOperationStatus())
                .unitOperationStatusRate(PMStatus.valueOf(pmFormCreateDTO.getUnitOperationStatusRate().toUpperCase()))
                .checkBelts(pmFormCreateDTO.isCheckBelts())
                .checkDuctWork(pmFormCreateDTO.isCheckDuctWork())
                .checkThermostat(pmFormCreateDTO.isCheckThermostat())
                .checkAmps(pmFormCreateDTO.isCheckAmps())
                .splitTemperature(pmFormCreateDTO.isSplitTemperature())
                .airFilters(pmFormCreateDTO.isAirFilters())
                .checkFuses(pmFormCreateDTO.isCheckFuses())
                .checkBeltsRate(PMStatus.valueOf(pmFormCreateDTO.getCheckBeltsRate().toUpperCase()))
                .checkDuctWorkRate(PMStatus.valueOf(pmFormCreateDTO.getCheckDuctWorkRate().toUpperCase()))
                .checkThermostatRate(PMStatus.valueOf(pmFormCreateDTO.getCheckThermostatRate().toUpperCase()))
                .checkAmpsRate(PMStatus.valueOf(pmFormCreateDTO.getCheckAmpsRate().toUpperCase()))
                .splitTemperatureRate(PMStatus.valueOf(pmFormCreateDTO.getSplitTemperatureRate().toUpperCase()))
                .airFiltersRate(PMStatus.valueOf(pmFormCreateDTO.getAirFiltersRate().toUpperCase()))
                .checkFusesRate(PMStatus.valueOf(pmFormCreateDTO.getCheckFusesRate().toUpperCase()))
                .build();
    }

    public List<PMFormEditDTO> PMFormToPMFormListEditDTO(List<PMForm> pmForms) {
        return pmForms.stream()
                .map(this::PMFormTOPMEditDTO)
                .collect(Collectors.toList());
    }

    public PMFormEditDTO PMFormTOPMEditDTO(PMForm pmForm){
        return PMFormEditDTO.builder()
                .pmformid(pmForm.getPMFormId().toString())
                .pmcheckid(pmForm.getPmcheck().getPMCheckId().toString())
                .age(pmForm.getAge())
                .brand(pmForm.getBrand())
                .checkCapacitors(pmForm.isCheckCapacitors())
                .checkCapacitorsRate(pmForm.getCheckCapacitorsRate().toString())
                .checkMotors(pmForm.isCheckMotors())
                .checkMotorsRate(pmForm.getCheckMotorsRate().toString())
                .checkPowerSystem(pmForm.isCheckPowerSystem())
                .checkPowerSystemRate(pmForm.getCheckPowerSystemRate().toString())
                .checkRefrigerant(pmForm.isCheckRefrigerant())
                .checkRefrigerantRate(pmForm.getCheckRefrigerantRate().toString())
                .cleanCondensateCoil(pmForm.isCleanCondensateCoil())
                .cleanCondensateCoilRate(pmForm.getCleanCondensateCoilRate().toString())
                .cleanCondensatePipe(pmForm.isCleanCondensatePipe())
                .cleanCondensatePipeRate(pmForm.getCleanCondensatePipeRate().toString())
                .inspectContactor(pmForm.isInspectContactor())
                .inspectContactorRate(pmForm.getInspectContactorRate().toString())
                .inspectEvapCoil(pmForm.isInspectEvapCoil())
                .inspectEvapCoilRate(pmForm.getInspectEvapCoilRate().toString())
                .modelCondenser(pmForm.getModelCondenser())
                .modelEvaporatorCoil(pmForm.getModelEvaporatorCoil())
                .modelFurnance(pmForm.getModelFurnance())
                .modelPackageUnit(pmForm.getModelPackageUnit())
                .refrigerantType(pmForm.getRefrigerantType())
                .notes(pmForm.getNotes())
                .powerUnitOn(pmForm.isPowerUnitOn())
                .powerUnitOnRate(pmForm.getPowerUnitOnRate().toString())
                .takePictures(pmForm.isTakePictures())
                .takePicturesRate(pmForm.getTakePicturesRate().toString())
                .tonnage(pmForm.getTonnage())
                .turnSystemCoolMode(pmForm.isTurnSystemCoolMode())
                .turnSystemCoolModeRate(pmForm.getTurnSystemCoolModeRate().toString())
                .type(pmForm.getType())
                .unitOperationStatus(pmForm.isUnitOperationStatus())
                .unitOperationStatusRate(pmForm.getUnitOperationStatusRate().toString())
                .checkBelts(pmForm.isCheckBelts())
                .checkDuctWork(pmForm.isCheckDuctWork())
                .checkThermostat(pmForm.isCheckThermostat())
                .checkAmps(pmForm.isCheckAmps())
                .splitTemperature(pmForm.isSplitTemperature())
                .airFilters(pmForm.isAirFilters())
                .checkFuses(pmForm.isCheckFuses())
                .checkBeltsRate(pmForm.getCheckBeltsRate().toString())
                .checkDuctWorkRate(pmForm.getCheckDuctWorkRate().toString())
                .checkThermostatRate(pmForm.getCheckThermostatRate().toString())
                .checkAmpsRate(pmForm.getCheckAmpsRate().toString())
                .splitTemperatureRate(pmForm.getSplitTemperatureRate().toString())
                .airFiltersRate(pmForm.getAirFiltersRate().toString())
                .checkFusesRate(pmForm.getCheckFusesRate().toString())
                //.images(pmForm.getImages().stream().map(this::imageToImageDTO).collect(Collectors.toList()))
                .images(Optional.ofNullable(pmForm.getImages())
                .map(images -> images.stream().map(this::imageToImageDTO).collect(Collectors.toList())).orElse(Collections.emptyList()))
        .build();
    }

    PMImageDTO imageToImageDTO(PMImage image){
        return PMImageDTO.builder()
        .imageId(image.getImageId().toString())
        .url(image.getUrl())
        .build();
    }

    public List<PMForm> listPMFormEditDTOtoListPMForm(List<PMFormEditDTO> listDTO) {
        return listDTO.stream()
                .map(this::PMEditDTOtoPMForm)
                .collect(Collectors.toList());
    }

    PMForm PMEditDTOtoPMForm(PMFormEditDTO pmFormEditDTO) {
        return PMForm.builder()
        .PMFormId(UUID.fromString(pmFormEditDTO.getPmformid()))
        .pmcheck(new PMCheck(UUID.fromString(pmFormEditDTO.getPmcheckid())))
        .age(pmFormEditDTO.getAge())
        .brand(pmFormEditDTO.getBrand())
        .checkCapacitors(pmFormEditDTO.isCheckCapacitors())
        .checkCapacitorsRate(PMStatus.valueOf(pmFormEditDTO.getCheckCapacitorsRate().toUpperCase()))
        .checkMotors(pmFormEditDTO.isCheckMotors())
        .checkMotorsRate(PMStatus.valueOf(pmFormEditDTO.getCheckMotorsRate().toUpperCase()))
        .checkPowerSystem(pmFormEditDTO.isCheckPowerSystem())
        .checkPowerSystemRate(PMStatus.valueOf(pmFormEditDTO.getCheckPowerSystemRate().toUpperCase()))
        .checkRefrigerant(pmFormEditDTO.isCheckRefrigerant())
        .checkRefrigerantRate(PMStatus.valueOf(pmFormEditDTO.getCheckRefrigerantRate().toUpperCase()))
        .cleanCondensateCoil(pmFormEditDTO.isCleanCondensateCoil())
        .cleanCondensateCoilRate(PMStatus.valueOf(pmFormEditDTO.getCleanCondensateCoilRate().toUpperCase()))
        .cleanCondensatePipe(pmFormEditDTO.isCleanCondensatePipe())
        .cleanCondensatePipeRate(PMStatus.valueOf(pmFormEditDTO.getCleanCondensatePipeRate().toUpperCase()))
        .inspectContactor(pmFormEditDTO.isInspectContactor())
        .inspectContactorRate(PMStatus.valueOf(pmFormEditDTO.getInspectContactorRate().toUpperCase()))
        .inspectEvapCoil(pmFormEditDTO.isInspectEvapCoil())
        .inspectEvapCoilRate(PMStatus.valueOf(pmFormEditDTO.getInspectEvapCoilRate().toUpperCase()))
        .modelCondenser(pmFormEditDTO.getModelCondenser())
        .modelEvaporatorCoil(pmFormEditDTO.getModelEvaporatorCoil())
        .modelFurnance(pmFormEditDTO.getModelFurnance())
        .modelPackageUnit(pmFormEditDTO.getModelPackageUnit())
        .refrigerantType(pmFormEditDTO.getRefrigerantType())
        .notes(pmFormEditDTO.getNotes())
        .powerUnitOn(pmFormEditDTO.isPowerUnitOn())
        .powerUnitOnRate(PMStatus.valueOf(pmFormEditDTO.getPowerUnitOnRate().toUpperCase()))
        .takePictures(pmFormEditDTO.isTakePictures())
        .takePicturesRate(PMStatus.valueOf(pmFormEditDTO.getTakePicturesRate().toUpperCase()))
        .tonnage(pmFormEditDTO.getTonnage())
        .turnSystemCoolMode(pmFormEditDTO.isTurnSystemCoolMode())
        .turnSystemCoolModeRate(PMStatus.valueOf(pmFormEditDTO.getTurnSystemCoolModeRate().toUpperCase()))
        .type(pmFormEditDTO.getType())
        .unitOperationStatus(pmFormEditDTO.isUnitOperationStatus())
        .unitOperationStatusRate(PMStatus.valueOf(pmFormEditDTO.getUnitOperationStatusRate().toUpperCase()))
        .checkBelts(pmFormEditDTO.isCheckBelts())
        .checkDuctWork(pmFormEditDTO.isCheckDuctWork())
        .checkThermostat(pmFormEditDTO.isCheckThermostat())
        .checkAmps(pmFormEditDTO.isCheckAmps())
        .splitTemperature(pmFormEditDTO.isSplitTemperature())
        .airFilters(pmFormEditDTO.isAirFilters())
        .checkFuses(pmFormEditDTO.isCheckFuses())
        .checkBeltsRate(PMStatus.valueOf(pmFormEditDTO.getCheckBeltsRate().toUpperCase()))
        .checkDuctWorkRate(PMStatus.valueOf(pmFormEditDTO.getCheckDuctWorkRate().toUpperCase()))
        .checkThermostatRate(PMStatus.valueOf(pmFormEditDTO.getCheckThermostatRate().toUpperCase()))
        .checkAmpsRate(PMStatus.valueOf(pmFormEditDTO.getCheckAmpsRate().toUpperCase()))
        .splitTemperatureRate(PMStatus.valueOf(pmFormEditDTO.getSplitTemperatureRate().toUpperCase()))
        .airFiltersRate(PMStatus.valueOf(pmFormEditDTO.getAirFiltersRate().toUpperCase()))
        .checkFusesRate(PMStatus.valueOf(pmFormEditDTO.getCheckFusesRate().toUpperCase()))
        .build();
    }

    public PMCheck PMCheckEditDTOtoPMCheck(PMCheckEditDTO pMCheckEditDTO) {
        return PMCheck.builder()
        .PMCheckId(UUID.fromString(pMCheckEditDTO.getPmcheckId()))
        .customer(pMCheckEditDTO.getCustomer())
        .inspectionFee(pMCheckEditDTO.getInspectionFee())
        .recomendationTotalPrice(pMCheckEditDTO.getRecomendationTotalPrice())
        .address(pMCheckEditDTO.getAddress())
        .build();
    }
}
