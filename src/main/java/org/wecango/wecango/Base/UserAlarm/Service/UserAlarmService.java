package org.wecango.wecango.Base.UserAlarm.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.FireBase.FcmMessageReqDto;
import org.wecango.wecango.Base.FireBase.FireBaseService;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.UserAlarm.Domain.UserAlarm;
import org.wecango.wecango.Base.UserAlarm.Dto.UserAlarmReqDto;
import org.wecango.wecango.Base.UserAlarm.Dto.UserAlarmResDto;
import org.wecango.wecango.Base.UserAlarm.Repository.UserAlarmDataRepository;
import org.wecango.wecango.Base.UserAlarm.Repository.UserAlarmQueryRepository;
import org.wecango.wecango.Base.UserBookMarkingCountry.Repository.UserBookMarkingCountryDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAlarmService {

    final UserAlarmDataRepository userAlarmDataRepository;

    final UserAlarmQueryRepository userAlarmQueryRepository;

    final MemberManagementDataRepository memberManagementDataRepository;

    final UserBookMarkingCountryDataRepository userBookMarkingCountryDataRepository;

    final NationControlDataRepository nationControlDataRepository;

    final FireBaseService fireBaseService;

    public void sendAlarm(UserAlarmReqDto reqDto) throws JsonProcessingException, FirebaseMessagingException {

        List<MemberManagement> sendList;

       NationControl relativeNation = null;

        if(reqDto.getNationId() == null){
            sendList = memberManagementDataRepository.findAll();
            FcmMessageReqDto fcmMessageReqDto = new FcmMessageReqDto();
            fcmMessageReqDto.setMessage(reqDto.getMessage());
            fcmMessageReqDto.setLinkUrl("https://app.wecango.org");
            fcmMessageReqDto.setTitle("wecango");
            fcmMessageReqDto.setMessage(reqDto.getMessage());
            fcmMessageReqDto.setType("All");
            fireBaseService.sendMessage(fcmMessageReqDto);
        }else {
            relativeNation = nationControlDataRepository.findById(reqDto.getNationId()).get();
            sendList = userBookMarkingCountryDataRepository.findByNationId(relativeNation)
                    .stream().map(x -> {
                        return x.getUserUid();
                    }).collect(Collectors.toList());
        }

        for (MemberManagement user : sendList ) {

            UserAlarm alarm = UserAlarm.builder()
                    .userUid(user)
                    .message(reqDto.getMessage())
                    .readFlag(false)
                    .sendDateTime(LocalDateTime.now())
                    .relationNationId(relativeNation)
                    .build();

            userAlarmDataRepository.save(alarm);
            FcmMessageReqDto fcmMessageReqDto = new FcmMessageReqDto();
            fcmMessageReqDto.setMessage(reqDto.getMessage());
            fcmMessageReqDto.setLinkUrl("https://app.wecango.org/BM004/"+relativeNation);
            fcmMessageReqDto.setTitle("wecango");
            fcmMessageReqDto.setMessage(reqDto.getMessage());
            fcmMessageReqDto.setType("nation");
            fcmMessageReqDto.setUid(user.getUid());

            fireBaseService.sendMessage(fcmMessageReqDto);
        }

    }

    public List<UserAlarmResDto> myAlarms(MemberManagement memberManagement) {
        List<UserAlarm> alarms = userAlarmDataRepository.findByUserUidOrderBySendDateTimeDesc(memberManagement);
        ModelMapper model = new ModelMapper();
        List<UserAlarmResDto> collect = alarms.stream().map(x -> {
            return model.map(x, UserAlarmResDto.class);
        }).collect(Collectors.toList());
        return collect;
    }

    public Integer unReadCount(MemberManagement memberManagement) {
        return userAlarmDataRepository.countByUserUidAndReadFlagFalse(memberManagement);
    }

    public void readAlarm(MemberManagement memberManagement) {
        userAlarmQueryRepository.readAlarm(memberManagement);
    }
}
