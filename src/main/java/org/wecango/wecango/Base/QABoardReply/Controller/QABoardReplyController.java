package org.wecango.wecango.Base.QABoardReply.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyInsertReqDto;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyResDto;
import org.wecango.wecango.Base.QABoardReply.Service.QABoardReplyService;

import java.util.List;

@RestController
@RequestMapping("/QABoardReply")
@RequiredArgsConstructor
public class QABoardReplyController {

    final QABoardReplyService qaBoardReplyService;

    @GetMapping
    List<QABoardReplyResDto> getQaDocReplys(Integer qaDocId){
       return  qaBoardReplyService.getQaDocReplys(qaDocId);
    }

    @PostMapping
    public QABoardReplyResDto insert(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement,
                                     @RequestBody QABoardReplyInsertReqDto reqDto){
        return qaBoardReplyService.insert(memberManagement,reqDto);
    }

    @PostMapping("/changeRepresentativeComment")
    public void changeRepresentativeComment(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement,
                                            Integer replyNumber,Integer changeOrder){
        if(memberManagement.getRole().indexOf("Admin") >= 0){
            qaBoardReplyService.changeRepresentativeComment(replyNumber,changeOrder);
        }
    }
}
