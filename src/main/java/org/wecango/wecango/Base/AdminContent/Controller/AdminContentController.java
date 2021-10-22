package org.wecango.wecango.Base.AdminContent.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentResDto;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentSaveReqDto;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentSimpleResDto;
import org.wecango.wecango.Base.AdminContent.Service.AdminContentService;

import java.util.List;

@RestController
@RequestMapping("/AdminContent")
@RequiredArgsConstructor
public class AdminContentController {

    final AdminContentService adminContentService;

    @PostMapping("/save")
    public AdminContentResDto save(@RequestBody  AdminContentSaveReqDto reqDto){
        return adminContentService.save(reqDto);
    }

    @PostMapping("/update")
    public AdminContentResDto update(@RequestBody AdminContentSaveReqDto reqDto){
        return adminContentService.update(reqDto);
    }

    @GetMapping("/doc")
    public AdminContentResDto getDoc(Integer id){
        return adminContentService.getDoc(id);
    }

    @GetMapping("/docs")
    public List<AdminContentSimpleResDto> getDocs(){
        return adminContentService.getDocs();
    }

    @DeleteMapping
    void delDoc(Integer id){
        adminContentService.delDoc(id);
    }
}
