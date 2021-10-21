package org.wecango.wecango.Base.NationChangeAlarm.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationChangeAlarm.Domain.NationChangeAlarm;
import org.wecango.wecango.Base.NationChangeAlarm.Dto.NationChangeAlarmResDto;
import org.wecango.wecango.Base.NationChangeAlarm.Repository.NationChangeAlarmDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;

@Service
@RequiredArgsConstructor
public class NationChangeAlarmService {

    final NationChangeAlarmDataRepository nationChangeAlarmDataRepository;

    final NationControlDataRepository nationControlDataRepository;


    public NationChangeAlarmResDto save(MemberManagement memberManagement, Integer nationId) {

        NationControl nationControl = nationControlDataRepository.findById(nationId).get();

        NationChangeAlarm nationChangeAlarm = NationChangeAlarm.builder()
                .userUid(memberManagement)
                .nationId(nationControl)
                .build();

        ModelMapper modelMapper = new ModelMapper();
        NationChangeAlarm save = nationChangeAlarmDataRepository.save(nationChangeAlarm);

        return modelMapper.map(save,NationChangeAlarmResDto.class);
    }
}
