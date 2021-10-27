package org.wecango.wecango.Sechduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.UserAlarm.Repository.UserAlarmDataRepository;

import java.time.LocalDateTime;

@Component
@Transactional
@RequiredArgsConstructor
public class Scheduler {

    final UserAlarmDataRepository userAlarmDataRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void alramRemove() {
        LocalDateTime beforeDateTime = LocalDateTime.now().minusDays(5);
        userAlarmDataRepository.deleteAlarmAllBySendDateTimeBefore(beforeDateTime.toString());
    }

}
