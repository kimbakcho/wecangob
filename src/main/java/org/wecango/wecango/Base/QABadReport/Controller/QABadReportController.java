package org.wecango.wecango.Base.QABadReport.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.Common.PageReqDto;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportReqDto;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportResDto;
import org.wecango.wecango.Base.QABadReport.Service.QABadReportService;
import org.wecango.wecango.Security.AccountAdapter;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/getReports")
    public Page<QABadReportResDto> getReports(@RequestBody PageReqDto pageReqDto){
        List<Sort.Order> sorts = pageReqDto
                .getSorts()
                .stream()
                .map(x -> {
                    Sort.Direction direction = x.getDirection().equals("Desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(direction, x.getColumn());
                }).collect(Collectors.toList());
        PageRequest pageable = PageRequest
                .of(pageReqDto.getPage(), pageReqDto.getSize(), Sort.by(sorts));

        return qaBadReportService.getReports(pageable);
    }

    @DeleteMapping()
    public void deleteReportDoc(Integer id){
        qaBadReportService.deleteReportDoc(id);
    }


}
