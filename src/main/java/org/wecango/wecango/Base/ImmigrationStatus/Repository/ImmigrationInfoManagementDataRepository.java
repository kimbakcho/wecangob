package org.wecango.wecango.Base.ImmigrationStatus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;

public interface ImmigrationInfoManagementDataRepository extends JpaRepository<ImmigrationInfoManagement, Integer> {
}
