package org.wecango.wecango.Base.NationChangeAlarm.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.NationChangeAlarm.Domain.NationChangeAlarm;

public interface NationChangeAlarmDataRepository extends JpaRepository<NationChangeAlarm,Integer> {
    
}
