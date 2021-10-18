package org.wecango.wecango.Base.QABoard.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoard.Dto.QABoardInsertDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;
import org.wecango.wecango.Base.QABoard.Service.QABoardService;

@RestController
@RequestMapping("/QABoard")
@RequiredArgsConstructor
public class QABoardController {

    final QABoardService qaBoardService;

    @PostMapping
    QABoardResDto insert(@AuthenticationPrincipal(expression = "memberManagement")MemberManagement memberManagement,@RequestBody QABoardInsertDto qaBoardInsertDto) {
        return qaBoardService.insert(memberManagement,qaBoardInsertDto);
    }

    @GetMapping
    QABoardResDto getDoc(Integer id){
        return qaBoardService.getDoc(id);
    }

}
