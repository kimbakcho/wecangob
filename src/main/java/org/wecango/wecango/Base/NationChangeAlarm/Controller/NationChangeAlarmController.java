package org.wecango.wecango.Base.NationChangeAlarm.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationChangeAlarm.Dto.NationChangeAlarmResDto;
import org.wecango.wecango.Base.NationChangeAlarm.Service.NationChangeAlarmService;

@RestController
@RequestMapping("/NationChangeAlarm")
@RequiredArgsConstructor
public class NationChangeAlarmController {

    final NationChangeAlarmService nationChangeAlarmService;

    @PostMapping("/save")
    public NationChangeAlarmResDto save(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement,
                                        Integer nationId){
        return nationChangeAlarmService.save(memberManagement,nationId);
    }
}
