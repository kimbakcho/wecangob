package org.wecango.wecango.Base.UserAlarm.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.UserAlarm.Dto.UserAlarmReqDto;
import org.wecango.wecango.Base.UserAlarm.Dto.UserAlarmResDto;
import org.wecango.wecango.Base.UserAlarm.Service.UserAlarmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/UserAlarm")
public class UserAlarmController {

    final UserAlarmService userAlarmService;

    @PostMapping("/sendAlarm")
    void sendAlarm(@RequestBody UserAlarmReqDto reqDto) throws JsonProcessingException, FirebaseMessagingException {
        userAlarmService.sendAlarm(reqDto);
    }

    @GetMapping("/myAlarms")
    List<UserAlarmResDto>  myAlarms(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement){
        return userAlarmService.myAlarms(memberManagement);
    }

    @GetMapping("/unReadCount")
    Integer unReadCount(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement){
        return userAlarmService.unReadCount(memberManagement);
    }

    @PostMapping("/readAlarm")
    void readAlarm(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement){
        userAlarmService.readAlarm(memberManagement);
    }



}
