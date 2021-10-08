package org.wecango.wecango.Base.ImmigrationStatus.Domain;

import lombok.*;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "immigrationStatus")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ImmigrationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @JoinColumn(name = "nationId")
    @ManyToOne()
    NationControl nationId;
    String continent;
    Boolean travelFlag;
    Integer mandatoryQuarantine;
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

    @JoinColumn(name = "vaccinatedLeavesCountry")
    @ManyToOne()
    ImmigrationInfoManagement vaccinatedLeavesCountry;
    @JoinColumn(name = "vaccinatedReturnHome")
    @ManyToOne()
    ImmigrationInfoManagement vaccinatedReturnHome;
    @JoinColumn(name = "unvaccinatedLeavesCountry")
    @ManyToOne()
    ImmigrationInfoManagement unvaccinatedLeavesCountry;
    @JoinColumn(name = "unvaccinatedReturnHome")
    @ManyToOne()
    ImmigrationInfoManagement unvaccinatedReturnHome;
}
