package org.wecango.wecango.Base.ImmigrationStatus.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusDetailResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusUpdateReqDto;
import org.wecango.wecango.Base.ImmigrationStatus.Service.ImmigrationStatusService;

import java.util.List;

@RestController
@RequestMapping("/ImmigrationStatus")
@RequiredArgsConstructor
public class ImmigrationStatusController {

    final ImmigrationStatusService immigrationStatusService;

    @GetMapping("/nation")
    public ImmigrationStatusDetailResDto getNationInfo(Integer id){
        return immigrationStatusService.getNationInfo(id);
    }

    @GetMapping("/adminMainRecommend")
    public List<ImmigrationStatusSimpleResDto> getAdminMainRecommend(){
        return immigrationStatusService.getAdminMainRecommend();
    }

    @GetMapping("/allInfo")
    public List<ImmigrationStatusSimpleResDto> allInfo(){
        return immigrationStatusService.AllInfo();
    }

    @PostMapping("/update")
    public ImmigrationStatusDetailResDto update(@RequestBody ImmigrationStatusUpdateReqDto reqDto){
        return immigrationStatusService.update(reqDto);
    }

}
