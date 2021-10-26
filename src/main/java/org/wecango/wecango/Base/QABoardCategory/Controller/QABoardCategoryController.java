package org.wecango.wecango.Base.QABoardCategory.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryNameChangeReqDto;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryOrderChangReqDto;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryResDto;
import org.wecango.wecango.Base.QABoardCategory.Service.QABoardCategoryService;
import org.wecango.wecango.Security.AccountAdapter;

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

    @PostMapping
    QABoardCategoryResDto inputName(String name){
        return qaBoardCategory.inputName(name);
    }

    @PostMapping("/changeName")
    QABoardCategoryResDto changeName(@RequestBody QABoardCategoryNameChangeReqDto reqDto){
        return qaBoardCategory.changeName(reqDto);
    }

    @PostMapping("/changeOrder")
    void changeOrder(@RequestBody QABoardCategoryOrderChangReqDto reqDto){
        qaBoardCategory.changeOrder(reqDto);
    }

    @DeleteMapping
    void deleteItem(@AuthenticationPrincipal AccountAdapter accountAdapter,  String categoryName){
        if(accountAdapter != null && accountAdapter.getMemberManagement().getRole().indexOf("Admin")>=0 ){
            qaBoardCategory.deleteItem(categoryName);
        }
    }

}
