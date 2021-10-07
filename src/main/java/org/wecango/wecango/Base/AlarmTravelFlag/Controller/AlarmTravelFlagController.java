package org.wecango.wecango.Base.AlarmTravelFlag.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.AlarmTravelFlag.Dto.AlarmTravelFlagResDto;
import org.wecango.wecango.Base.AlarmTravelFlag.Service.AlarmTravelFlagService;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Security.AccountAdapter;

@RestController
@RequestMapping("/AlarmTravelFlag")
@RequiredArgsConstructor
public class AlarmTravelFlagController {

    final AlarmTravelFlagService alarmTravelFlagService;

    @GetMapping("/hasFlag")
    public AlarmTravelFlagResDto hasTravelFlag(@AuthenticationPrincipal AccountAdapter accountAdapter,
                                               Integer nationId){
        if(accountAdapter != null){
            return alarmTravelFlagService.hasTravelFlag(accountAdapter.getMemberManagement(),nationId);
        }else {
            return  null;
        }
    }

    @PostMapping("/saveTravelFlag")
    public void saveTravelFlag(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,
                               Integer nationId){
        alarmTravelFlagService.saveTravelFlag(memberManagement,nationId);
    }

    @DeleteMapping("/deleteTravelFlag")
    public void deleteTravelFlag(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,
                                 Integer nationId){
        alarmTravelFlagService.deleteTravelFlag(memberManagement,nationId);
    }
}
