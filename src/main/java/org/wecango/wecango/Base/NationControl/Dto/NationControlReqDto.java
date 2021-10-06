package org.wecango.wecango.Base.NationControl.Dto;

import lombok.Data;

import java.util.List;

@Data
public class NationControlReqDto {
    List<String> continent;
    Boolean travelFlag;
    Boolean visaFlag;
    Boolean covidTest;
    List<String> benefitsVaccination;
    Boolean possibleExempted;
    Integer mandatoryQuarantineFrom;
    Integer mandatoryQuarantineTo;

}
