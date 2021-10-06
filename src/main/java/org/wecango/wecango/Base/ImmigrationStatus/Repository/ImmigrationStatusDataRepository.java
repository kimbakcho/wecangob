package org.wecango.wecango.Base.ImmigrationStatus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import java.util.List;

public interface ImmigrationStatusDataRepository extends JpaRepository<ImmigrationStatus, Integer> {
    ImmigrationStatus getByNationId(NationControl nationControl);
    List<ImmigrationStatus> findByRecommendedCountryOrderByRecommendedCountryOrderDesc(Boolean recommendedCountry);

}
