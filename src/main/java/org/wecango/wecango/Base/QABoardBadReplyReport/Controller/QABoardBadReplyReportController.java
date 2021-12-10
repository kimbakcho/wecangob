package org.wecango.wecango.Base.QABoardBadReplyReport.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.QABoardBadReplyReport.Dto.QABoardBadReplyReportResDto;
import org.wecango.wecango.Base.QABoardBadReplyReport.Service.QABoardBadReplyReportService;
import org.wecango.wecango.Security.AccountAdapter;

@RestController
@RequestMapping( "/QABoardBadReplyReport")
@RequiredArgsConstructor
public class QABoardBadReplyReportController {

    final QABoardBadReplyReportService qaBoardBadReplyReportService;

    @PostMapping
    public QABoardBadReplyReportResDto report(Integer id,
                                              @AuthenticationPrincipal AccountAdapter accountAdapter){
        return qaBoardBadReplyReportService.report(id,accountAdapter.getMemberManagement());
    }


}
