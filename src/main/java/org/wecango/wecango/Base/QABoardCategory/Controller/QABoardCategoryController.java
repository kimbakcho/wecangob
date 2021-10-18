package org.wecango.wecango.Base.QABoardCategory.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryResDto;
import org.wecango.wecango.Base.QABoardCategory.Service.QABoardCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qaBoardCategory")
public class QABoardCategoryController {

    final QABoardCategoryService qaBoardCategory;

    @GetMapping
    List<QABoardCategoryResDto> getList(){
        return qaBoardCategory.getList();
    }

}
