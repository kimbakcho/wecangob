package org.wecango.wecango.Base.NationChangeAlarm.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.NationChangeAlarm.Domain.NationChangeAlarm;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import java.util.List;

public interface NationChangeAlarmDataRepository extends JpaRepository<NationChangeAlarm,Integer> {
    List<NationChangeAlarm> findByNationId(NationControl nationControl);
}
