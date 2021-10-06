package org.wecango.wecango.Base.MemberManagement.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;

@RestController
@RequestMapping("/MemberManagement")
public class MemberManagementController {

    @GetMapping("/me")
    public MemberManagementSimpleResDto getMe(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement){
        ModelMapper modelMapper = new ModelMapper();
        MemberManagementSimpleResDto map = modelMapper.map(memberManagement, MemberManagementSimpleResDto.class);
        return map;
    }
}
