package org.wecango.wecango.Base.UserAlarm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.UserAlarm.Domain.UserAlarm;

import java.util.List;

public interface UserAlarmDataRepository extends JpaRepository<UserAlarm,Integer> {

    List<UserAlarm> findByUserUidOrderBySendDateTimeDesc(MemberManagement userUid);
    Integer countByUserUidAndReadFlagFalse(MemberManagement userUid);
}
