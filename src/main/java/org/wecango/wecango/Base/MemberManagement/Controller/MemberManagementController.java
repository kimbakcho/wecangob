package org.wecango.wecango.Base.MemberManagement.Controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.FcmTokenUpdateReqDto;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Service.MemberManagementService;
import org.wecango.wecango.Security.AccountAdapter;

@RestController
@RequestMapping("/MemberManagement")
@RequiredArgsConstructor
public class MemberManagementController {

    final MemberManagementService memberManagementService;

    @GetMapping("/me")
    public MemberManagementSimpleResDto getMe(@AuthenticationPrincipal AccountAdapter accountAdapter){
        if(accountAdapter == null){
            return null;
        }else {
            ModelMapper modelMapper = new ModelMapper();
            MemberManagementSimpleResDto map = modelMapper.map(accountAdapter.getMemberManagement(), MemberManagementSimpleResDto.class);
            return map;
        }
    }

    @GetMapping("/checkNickName")
    public boolean checkNickName(String nickName){
        return memberManagementService.checkNickName(nickName);
    }

    @PostMapping("/fcmToken")
    public String updateFcmToken(@RequestBody FcmTokenUpdateReqDto fcmTokenUpdateReqDto) throws FirebaseMessagingException {
        return memberManagementService.updateFcmToken(fcmTokenUpdateReqDto);
    }

    @PostMapping("/changeNickName")
    public void changeNickName(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,
                                 String nickName){
        memberManagementService.changeNickName(memberManagement,nickName);
    }

    @PostMapping("/changeProfileImage")
    public MemberManagementSimpleResDto changeProfileImage(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,
                                                           String imageUrl){
        return memberManagementService.changeProfileImage(memberManagement,imageUrl);
    }

}
