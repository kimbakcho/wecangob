package org.wecango.wecango.Base.AlarmTravelFlag.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.AlarmTravelFlag.Domain.AlarmTravelFlag;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import java.util.Optional;

public interface AlarmTravelFlagDataRepository extends JpaRepository<AlarmTravelFlag, Integer> {

    Optional<AlarmTravelFlag> findByUserUidAndNationId(MemberManagement userUid, NationControl nationId);
    void deleteByUserUidAndNationId(MemberManagement userUid, NationControl nationId);

}
