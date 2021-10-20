package org.wecango.wecango.Base.QABoard.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoard.Dto.QABoardComResDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardFilterReqDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardInsertDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;
import org.wecango.wecango.Base.QABoard.Service.QABoardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/QABoard")
@RequiredArgsConstructor
public class QABoardController {

    final QABoardService qaBoardService;

    @PostMapping
    QABoardResDto insert(@AuthenticationPrincipal(expression = "memberManagement") MemberManagement memberManagement, @RequestBody QABoardInsertDto qaBoardInsertDto) {
        return qaBoardService.insert(memberManagement, qaBoardInsertDto);
    }

    @PostMapping("/viewCount")
    void viewCount(Integer id) {
        qaBoardService.viewCount(id);
    }

    @GetMapping
    QABoardResDto getDoc(Integer id) {
        return qaBoardService.getDoc(id);
    }

    @PostMapping("/getFilterDoc")
    Page<QABoardComResDto> getFilterDoc(@RequestBody QABoardFilterReqDto reqDto) {

        List<Sort.Order> sorts = reqDto.getPageReqDto()
                .getSorts()
                .stream()
                .map(x -> {
                    Sort.Direction direction = x.getDirection().equals("Desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(direction, x.getColumn());
                }).collect(Collectors.toList());

        PageRequest pageable = PageRequest
                .of(reqDto.getPageReqDto().getPage(), reqDto.getPageReqDto().getSize(), Sort.by(sorts));

        return qaBoardService.getFilterDoc(reqDto, pageable);
    }

}
