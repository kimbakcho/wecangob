package org.wecango.wecango.Base.ImmigrationStatus.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusDetailResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
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
}
