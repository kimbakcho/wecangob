package org.wecango.wecango.Base.ImmigrationStatus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

public interface ImmigrationInfoManagementDataRepository extends JpaRepository<ImmigrationInfoManagement, Integer> {
    ImmigrationInfoManagement getByNationId(NationControl nationId);
    ImmigrationInfoManagement getByNationIdAndClassification(NationControl nationId,String classification);
}
