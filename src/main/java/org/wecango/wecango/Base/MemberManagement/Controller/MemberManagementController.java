package org.wecango.wecango.Base.MemberManagement.Controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Service.MemberManagementService;

@RestController
@RequestMapping("/MemberManagement")
@RequiredArgsConstructor
public class MemberManagementController {

    final MemberManagementService memberManagementService;

    @GetMapping("/me")
    public MemberManagementSimpleResDto getMe(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement){
        ModelMapper modelMapper = new ModelMapper();
        MemberManagementSimpleResDto map = modelMapper.map(memberManagement, MemberManagementSimpleResDto.class);
        return map;
    }

    @PostMapping("/changeProfileImage")
    public MemberManagementSimpleResDto changeProfileImage(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,
                                                           String imageUrl){
        return memberManagementService.changeProfileImage(memberManagement,imageUrl);
    }

}
