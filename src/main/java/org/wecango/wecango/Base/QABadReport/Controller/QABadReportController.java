package org.wecango.wecango.Base.QABadReport.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportReqDto;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportResDto;
import org.wecango.wecango.Base.QABadReport.Service.QABadReportService;
import org.wecango.wecango.Security.AccountAdapter;

@RequestMapping("/QABadReport")
@RestController
@RequiredArgsConstructor
public class QABadReportController {

    final QABadReportService qaBadReportService;

    @PostMapping
    public QABadReportResDto report(@AuthenticationPrincipal AccountAdapter accountAdapter,
                                    @RequestBody QABadReportReqDto qaBadReportReqDto){
        if(accountAdapter!=null){
            return qaBadReportService.save(qaBadReportReqDto,accountAdapter.getMemberManagement());
        }else {
            throw new AccessDeniedException("로그인이 필요 합니다.");
        }

    }





}
