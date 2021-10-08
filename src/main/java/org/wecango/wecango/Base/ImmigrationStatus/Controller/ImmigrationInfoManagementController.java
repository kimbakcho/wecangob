package org.wecango.wecango.Base.ImmigrationStatus.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationInfoManagementResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationInfoManagementUpdateReqDto;
import org.wecango.wecango.Base.ImmigrationStatus.Service.ImmigrationInfoManagementService;

@RestController
@RequestMapping("/ImmigrationInfoManagement")
@RequiredArgsConstructor
public class ImmigrationInfoManagementController {

    final ImmigrationInfoManagementService immigrationInfoManagementService;

    @GetMapping()
    public ImmigrationInfoManagementResDto getDoc(Integer id){
        return immigrationInfoManagementService.getDoc(id);
    }

    @PostMapping("/update")
    public ImmigrationInfoManagementResDto updateDoc(@RequestBody ImmigrationInfoManagementUpdateReqDto reqDto){
        return immigrationInfoManagementService.updateDoc(reqDto);
    }
}
