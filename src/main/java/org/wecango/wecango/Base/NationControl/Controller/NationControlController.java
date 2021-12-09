package org.wecango.wecango.Base.NationControl.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.NationControl.Dto.NationControlReqDto;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;
import org.wecango.wecango.Base.NationControl.Service.NationControlService;

import java.util.List;

@RestController
@RequestMapping("/NationControl")
@RequiredArgsConstructor
public class NationControlController {

    final NationControlService nationControlService;

    @PostMapping("/filter")
    List<NationControlResDto> getFilter(@RequestBody NationControlReqDto reqDto){
        return nationControlService.getFilter(reqDto);
    }

    @GetMapping("/nationInfo")
    NationControlResDto getNationInfo(Integer id){
        return nationControlService.getNationInfo(id);
    }

    @PostMapping("/displayFlag")
    void setDisplayFlag(Integer id,Boolean flag){
        nationControlService.setDisplayFlag(id,flag);
    }

}
