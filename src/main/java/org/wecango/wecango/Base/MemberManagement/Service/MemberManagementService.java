package org.wecango.wecango.Base.MemberManagement.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberManagementService {
    final MemberManagementDataRepository memberManagementDataRepository;

    public MemberManagementSimpleResDto changeProfileImage(MemberManagement memberManagement, String imageUrl) {
        MemberManagement byId = memberManagementDataRepository.findById(memberManagement.getUid()).get();
        byId.setProfileImage(imageUrl);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId, MemberManagementSimpleResDto.class);
    }
}
