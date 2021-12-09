package org.wecango.wecango.Base.MemberManagement.Controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.Common.ErrorResult;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.*;
import org.wecango.wecango.Base.MemberManagement.Service.MemberManagementService;
import org.wecango.wecango.Security.AccountAdapter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/MemberManagement")
@RequiredArgsConstructor
public class MemberManagementController {

    final MemberManagementService memberManagementService;

    @GetMapping("/me")
    public MemberManagementSimpleResDto getMe(@AuthenticationPrincipal AccountAdapter accountAdapter){
        if(accountAdapter == null){
            return null;
        } else {
            ModelMapper modelMapper = new ModelMapper();
            MemberManagementSimpleResDto map = modelMapper.map(accountAdapter.getMemberManagement(), MemberManagementSimpleResDto.class);
            return map;
        }
    }

    @GetMapping("/isJoined")
    public boolean isJoined(String uid){
        return memberManagementService.isJoined(uid);
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

    @PostMapping("/users")
    public Page<MemberManagementResDto> getMemberList(@RequestBody MemberSearchReqDto memberSearchReqDto){

        List<Sort.Order> sorts = memberSearchReqDto.getPageReqDto()
                .getSorts()
                .stream()
                .map(x -> {
                    Sort.Direction direction = x.getDirection().equals("Desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(direction, x.getColumn());
                }).collect(Collectors.toList());

        PageRequest pageable = PageRequest
                .of(memberSearchReqDto.getPageReqDto().getPage(), memberSearchReqDto.getPageReqDto().getSize(), Sort.by(sorts));
        return memberManagementService.getMemberList(memberSearchReqDto,pageable);
    }

    @DeleteMapping("/user")
    void deleteMember(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement,String uid){
        if(memberManagement.getRole().indexOf("Admin") >= 0) {
            memberManagementService.deleteMember(uid);
        }
    }

    @PostMapping("/join")
    public MemberJoinResDto join(@RequestBody MemberJoinReqDto joinReqDto) throws java.nio.file.AccessDeniedException {
        return memberManagementService.join(joinReqDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler()
    public ErrorResult illegalExHandle(Exception e) {
        return new ErrorResult("BAD", e.getMessage());
    }

}
