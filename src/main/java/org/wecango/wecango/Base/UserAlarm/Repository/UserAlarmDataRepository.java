package org.wecango.wecango.Base.UserAlarm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.UserAlarm.Domain.UserAlarm;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAlarmDataRepository extends JpaRepository<UserAlarm,Integer> {

    List<UserAlarm> findByUserUidOrderBySendDateTimeDesc(MemberManagement userUid);
    Integer countByUserUidAndReadFlagFalse(MemberManagement userUid);
    @Modifying
    @Query(value = "delete from userAlarm  where sendDateTime <= :dateTime",nativeQuery = true)
    void deleteAlarmAllBySendDateTimeBefore(String dateTime);
}
