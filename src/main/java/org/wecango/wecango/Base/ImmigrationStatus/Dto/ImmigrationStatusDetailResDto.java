package org.wecango.wecango.Base.ImmigrationStatus.Dto;

import lombok.Data;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import java.time.LocalDateTime;

@Data
public class ImmigrationStatusDetailResDto {

    int id;
    NationControlResDto nation;
    String continent;
    Boolean travelFlag;
    String mandatoryQuarantine;
    LocalDateTime updateDateTime;
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
    ImmigrationInfoManagementResDto vaccinatedLeavesCountry;
    ImmigrationInfoManagementResDto vaccinatedReturnHome;
    ImmigrationInfoManagementResDto unvaccinatedLeavesCountry;
    ImmigrationInfoManagementResDto unvaccinatedReturnHome;

}
