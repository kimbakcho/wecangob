package org.wecango.wecango.Base.ImmigrationStatus.Dto;

import lombok.Data;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import java.time.LocalDateTime;

@Data
public class ImmigrationStatusSimpleResDto {
    int id;
    NationControlResDto nation;
    String continent;
    Boolean travelFlag;
    String mandatoryQuarantine;
    LocalDateTime updateDateTime;
    Boolean recommendedCountry;
    Integer recommendedCountryOrder;
    String recommendedCountryImageUrl;
    Integer vaccinationFlag;
    String pcrTest;
    String mandatoryQuarantineText;
    Boolean visaFlag;
    Boolean covidTest;
    String benefitsVaccination;
    Boolean possibleExempted;
}
