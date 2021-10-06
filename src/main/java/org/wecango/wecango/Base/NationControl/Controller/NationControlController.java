package org.wecango.wecango.Base.NationControl.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecango.wecango.Base.NationControl.Dto.NationControlReqDto;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;
import org.wecango.wecango.Base.NationControl.Service.NationControlService;

import java.util.List;

@RestController
@RequestMapping("/NationControl")
@RequiredArgsConstructor
public class NationControlController {

    final NationControlService nationControlService;

    @GetMapping("/filter")
    List<NationControlResDto> getFilter(NationControlReqDto reqDto){
        return nationControlService.getFilter(reqDto);
    }

}
