package org.wecango.wecango.Base.QABoardBadReplyReport.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.Common.PageReqDto;
import org.wecango.wecango.Base.QABoardBadReplyReport.Dto.QABoardBadReplyReportResDto;
import org.wecango.wecango.Base.QABoardBadReplyReport.Service.QABoardBadReplyReportService;
import org.wecango.wecango.Security.AccountAdapter;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/getReports")
    public Page<QABoardBadReplyReportResDto> getReports(@RequestBody PageReqDto pageReqDto){
        List<Sort.Order> sorts = pageReqDto
                .getSorts()
                .stream()
                .map(x -> {
                    Sort.Direction direction = x.getDirection().equals("Desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(direction, x.getColumn());
                }).collect(Collectors.toList());
        PageRequest pageable = PageRequest
                .of(pageReqDto.getPage(), pageReqDto.getSize(), Sort.by(sorts));

        return qaBoardBadReplyReportService.getReports(pageable);
    }
    @DeleteMapping
    public void deleteReportReply(Integer id){
        qaBoardBadReplyReportService.deleteReportReply(id);
    }


}
