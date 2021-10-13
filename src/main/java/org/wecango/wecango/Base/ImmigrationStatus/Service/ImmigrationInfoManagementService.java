package org.wecango.wecango.Base.ImmigrationStatus.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationInfoManagementResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationInfoManagementUpdateReqDto;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationInfoManagementDataRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ImmigrationInfoManagementService {

    final ImmigrationInfoManagementDataRepository immigrationInfoManagementDataRepository;

    public ImmigrationInfoManagementResDto getDoc(Integer id){
        ImmigrationInfoManagement byId = immigrationInfoManagementDataRepository.findById(id).get();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId, ImmigrationInfoManagementResDto.class);
    }

    public ImmigrationInfoManagementResDto updateDoc(ImmigrationInfoManagementUpdateReqDto reqDto) {
        ImmigrationInfoManagement byId = immigrationInfoManagementDataRepository.findById(reqDto.getId()).get();
        byId.setContentMarkDown(reqDto.getMarkDown());
        byId.setContentHtml(reqDto.getHtml());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId, ImmigrationInfoManagementResDto.class);
    }
}
