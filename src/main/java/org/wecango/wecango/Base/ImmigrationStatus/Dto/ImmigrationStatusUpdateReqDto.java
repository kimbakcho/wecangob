package org.wecango.wecango.Base.ImmigrationStatus.Dto;

import lombok.Data;

@Data
public class ImmigrationStatusUpdateReqDto {
    Integer id;
    String nationFlagImageUrl;
    String nationFlagImageFileName;
    String continent;
    Boolean travelFlag;
    String mandatoryQuarantine;
    Boolean recommendedCountry;
    Integer recommendedCountryOrder;
    String recommendedCountryImageUrl;
    String recommendedCountryImageFileName;
    Integer vaccinationFlag;
    String pcrTest;
    String mandatoryQuarantineText;
    Boolean visaFlag;
    Boolean covidTest;
    String benefitsVaccination;
    Boolean possibleExempted;
}
