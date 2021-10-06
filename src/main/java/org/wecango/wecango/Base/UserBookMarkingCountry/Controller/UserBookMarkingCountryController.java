package org.wecango.wecango.Base.UserBookMarkingCountry.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.UserBookMarkingCountry.Dto.UserBookMarkingCountryResDto;
import org.wecango.wecango.Base.UserBookMarkingCountry.Service.UserBookMarkingCountryService;
import org.wecango.wecango.Security.AccountAdapter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/UserBookMarkingCountry")
@RequiredArgsConstructor
public class UserBookMarkingCountryController {

    final UserBookMarkingCountryService userBookMarkingCountryService;

    @GetMapping("/isBookMarking")
    public UserBookMarkingCountryResDto isBookMarking(@AuthenticationPrincipal AccountAdapter accountAdapter,int nationId){
        if(accountAdapter == null){
            return null;
        }
        return userBookMarkingCountryService.isBookMarking(accountAdapter.getMemberManagement(),nationId);
    }

    @GetMapping("/BookMarkings")
    public List<UserBookMarkingCountryResDto> bookMarkingList(@AuthenticationPrincipal AccountAdapter accountAdapter){
        if(accountAdapter == null){
            return new ArrayList<>();
        }
        return userBookMarkingCountryService.bookMarkingList(accountAdapter.getMemberManagement());

    }


    @PostMapping("/bookMarking")
    UserBookMarkingCountryResDto bookMarking(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,int nationId){
        return userBookMarkingCountryService.bookMarking(memberManagement,nationId);
    }

    @DeleteMapping ("/bookUnMarking")
    void bookUnMarking(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,int nationId){
        userBookMarkingCountryService.bookUnMarking(memberManagement,nationId);
    }
}
