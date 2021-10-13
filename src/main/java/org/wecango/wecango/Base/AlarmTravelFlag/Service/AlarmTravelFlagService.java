package org.wecango.wecango.Base.AlarmTravelFlag.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.AlarmTravelFlag.Domain.AlarmTravelFlag;
import org.wecango.wecango.Base.AlarmTravelFlag.Dto.AlarmTravelFlagResDto;
import org.wecango.wecango.Base.AlarmTravelFlag.Repository.AlarmTravelFlagDataRepository;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmTravelFlagService {
    final AlarmTravelFlagDataRepository alarmTravelFlagDataRepository;

    final NationControlDataRepository nationControlDataRepository;

    public AlarmTravelFlagResDto hasTravelFlag(MemberManagement memberManagement, Integer nationId) {

        NationControl nationControl = nationControlDataRepository.findById(nationId).get();

        Optional<AlarmTravelFlag> alarmTravelFlagOptional = alarmTravelFlagDataRepository.findByUserUidAndNationId(memberManagement, nationControl);

        ModelMapper modelMapper = new ModelMapper();

        if(alarmTravelFlagOptional.isEmpty()){
            return null;
        }else {
            return modelMapper.map(alarmTravelFlagOptional.get(),AlarmTravelFlagResDto.class);
        }
    }

    public void saveTravelFlag(MemberManagement memberManagement, Integer nationId) {

        NationControl nationControl = nationControlDataRepository.findById(nationId).get();
        AlarmTravelFlag alarmTravelFlag = AlarmTravelFlag.builder()
                .nationId(nationControl)
                .userUid(memberManagement)
                .build();
        alarmTravelFlagDataRepository.save(alarmTravelFlag);
    }

    public void deleteTravelFlag(MemberManagement memberManagement, Integer nationId) {
        NationControl nationControl = nationControlDataRepository.findById(nationId).get();
        alarmTravelFlagDataRepository.deleteByUserUidAndNationId(memberManagement,nationControl);
    }
}
