package org.wecango.wecango.Base.ImmigrationStatus.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.FireBase.FcmMessageReqDto;
import org.wecango.wecango.Base.FireBase.FireBaseService;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusDetailResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusUpdateReqDto;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationInfoManagementDataRepository;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationStatusDataRepository;
import org.wecango.wecango.Base.NationChangeAlarm.Domain.NationChangeAlarm;
import org.wecango.wecango.Base.NationChangeAlarm.Repository.NationChangeAlarmDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.UserAlarm.Domain.UserAlarm;
import org.wecango.wecango.Base.UserAlarm.Repository.UserAlarmDataRepository;
import org.wecango.wecango.Base.UserBookMarkingCountry.Domain.UserBookMarkingCountry;
import org.wecango.wecango.Base.UserBookMarkingCountry.Repository.UserBookMarkingCountryDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImmigrationStatusService {
    final ImmigrationStatusDataRepository immigrationStatusDataRepository;
    final NationControlDataRepository nationControlDataRepository;
    final ImmigrationInfoManagementDataRepository immigrationInfoManagementDataRepository;
    final UserBookMarkingCountryDataRepository userBookMarkingCountryDataRepository;
//    final NationChangeAlarmDataRepository nationChangeAlarmDataRepository;
    final UserAlarmDataRepository userAlarmDataRepository;
    final FireBaseService fireBaseService;

    public ImmigrationStatusDetailResDto getNationInfo(Integer id){
        NationControl nationControl = nationControlDataRepository.findById(id).get();
        ImmigrationStatus byId = immigrationStatusDataRepository.getByNationId(nationControl);
        if(byId.getVaccinatedLeavesCountry()== null){
            ImmigrationInfoManagement vaccinatedLeavesCountry = ImmigrationInfoManagement.builder()
                    .classification("VaccinatedLeavesCountry")
                    .nationId(nationControl)
                    .contentHtml("")
                    .contentMarkDown("")
                    .updateDateTime(LocalDateTime.now())
                    .build();
            ImmigrationInfoManagement save = immigrationInfoManagementDataRepository.save(vaccinatedLeavesCountry);
            byId.setVaccinatedLeavesCountry(save);
        }
        if(byId.getVaccinatedReturnHome()== null){
            NationControl nation = nationControlDataRepository.getByNationName("대한민국");
            ImmigrationInfoManagement byNationId = immigrationInfoManagementDataRepository.getByNationIdAndClassification(nation,"VaccinatedReturnHome");
            byId.setVaccinatedReturnHome(byNationId);
        }
        if(byId.getUnvaccinatedLeavesCountry()== null){
            ImmigrationInfoManagement vaccinatedLeavesCountry = ImmigrationInfoManagement.builder()
                    .classification("UnvaccinatedLeavesCountry")
                    .nationId(nationControl)
                    .contentHtml("")
                    .contentMarkDown("")
                    .updateDateTime(LocalDateTime.now())
                    .build();
            ImmigrationInfoManagement save = immigrationInfoManagementDataRepository.save(vaccinatedLeavesCountry);
            byId.setUnvaccinatedLeavesCountry(save);
        }
        if(byId.getUnvaccinatedReturnHome()== null){
            NationControl nation = nationControlDataRepository.getByNationName("대한민국");
            ImmigrationInfoManagement byNationId = immigrationInfoManagementDataRepository.getByNationIdAndClassification(nation,"UnvaccinatedReturnHome");
            byId.setUnvaccinatedReturnHome(byNationId);
        }
        ModelMapper modelMapper = new ModelMapper();
        ImmigrationStatusDetailResDto resDto = modelMapper.map(byId, ImmigrationStatusDetailResDto.class);
        return resDto;
    }

    public List<ImmigrationStatusSimpleResDto> getAdminMainRecommend() {
        List<ImmigrationStatus> recommendedCountryOrderDesc = immigrationStatusDataRepository.findByRecommendedCountryOrderByRecommendedCountryOrderDesc(true);
        ModelMapper modelMapper = new ModelMapper();
        List<ImmigrationStatusSimpleResDto> collect = recommendedCountryOrderDesc.stream().map(x -> {
            return modelMapper.map(x, ImmigrationStatusSimpleResDto.class);
        }).collect(Collectors.toList());
        return collect;
    }

    public List<ImmigrationStatusSimpleResDto> AllInfo() {

        List<ImmigrationStatus> all = immigrationStatusDataRepository.findAll(Sort.by(Sort.Direction.ASC, "nationId.nationName"));
        ModelMapper modelMapper = new ModelMapper();
        List<ImmigrationStatusSimpleResDto> collect = all.stream().map(x -> {
            return modelMapper.map(x, ImmigrationStatusSimpleResDto.class);
        }).collect(Collectors.toList());
        return collect;
    }

    public ImmigrationStatusDetailResDto update(ImmigrationStatusUpdateReqDto reqDto) {
        ImmigrationStatus updateItem = immigrationStatusDataRepository.findById(reqDto.getId()).get();
        List<UserBookMarkingCountry> alarmUsers = userBookMarkingCountryDataRepository.findByNationId(updateItem.getNationId());

        if(reqDto.getNationFlagImageUrl() != null ){
            if(updateItem.getNationId() != null){
                updateItem.getNationId().setFlagImage(reqDto.getNationFlagImageUrl() );
                updateItem.getNationId().setFileName(reqDto.getNationFlagImageFileName());
            }
        }
        if(reqDto.getContinent() != null){
            updateItem.setContinent(reqDto.getContinent());
        }
        if(reqDto.getTravelFlag()!=null){
            if(hasChangeProperty(updateItem.getTravelFlag(),reqDto.getTravelFlag())){
                String optionName = reqDto.getTravelFlag() ? "가능" : "불가능";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"여행 가능 여부",
                        optionName,updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setTravelFlag(reqDto.getTravelFlag());
        }
        if(reqDto.getMandatoryQuarantine() != null){
            if(hasChangeProperty(updateItem.getMandatoryQuarantine(),reqDto.getMandatoryQuarantine())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"의무 격리",
                        reqDto.getMandatoryQuarantine(),updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setMandatoryQuarantine(reqDto.getMandatoryQuarantine());
        }
        if(reqDto.getRecommendedCountry() != null){
            updateItem.setRecommendedCountry(reqDto.getRecommendedCountry());
        }
        if(reqDto.getRecommendedCountryOrder() != null){
            updateItem.setRecommendedCountryOrder(reqDto.getRecommendedCountryOrder() );
        }
        if(reqDto.getRecommendedCountryImageUrl()!=null){
            updateItem.setRecommendedCountryImageUrl(reqDto.getRecommendedCountryImageUrl());
            updateItem.setRecommendedCountryImageFileName(reqDto.getRecommendedCountryImageFileName());
        }
        if(reqDto.getVaccinationFlag()!=null){
            if(hasChangeProperty(updateItem.getVaccinationFlag(),reqDto.getVaccinationFlag())){
                String optionName = "" ;
                if(reqDto.getVaccinationFlag() == 0){
                    optionName = "무관";
                }else if(reqDto.getVaccinationFlag() == 1){
                    optionName = "선택";
                }else if(reqDto.getVaccinationFlag() == 2){
                    optionName = "필수";
                }
                sendAlarmMessage(updateItem.getNationId().getNationName(),"백신 접종",
                        optionName,updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setVaccinationFlag(reqDto.getVaccinationFlag());

        }
        if(reqDto.getPcrTest() != null){
            if(hasChangeProperty(updateItem.getPcrTest(),reqDto.getPcrTest())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"PCR 검사",
                        reqDto.getPcrTest(),updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setPcrTest(reqDto.getPcrTest());

        }
        if(reqDto.getMandatoryQuarantineText() != null){
            if(hasChangeProperty(updateItem.getMandatoryQuarantineText(),reqDto.getMandatoryQuarantineText())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"의무 격리",
                        reqDto.getMandatoryQuarantineText(),updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setMandatoryQuarantineText(reqDto.getMandatoryQuarantineText());

        }
        if(reqDto.getPublicAnnouncement() != null){
            updateItem.setPublicAnnouncement(reqDto.getPublicAnnouncement());
        }
        if(reqDto.getVisaFlag() != null ){
            if(hasChangeProperty(updateItem.getVisaFlag(),reqDto.getVisaFlag())){
                String optionName = reqDto.getVisaFlag() ? "필요" : "불필요";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"Visa 필요 유무",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setVisaFlag(reqDto.getVisaFlag());
        }
        if(reqDto.getCovidTest() != null ){
            if(hasChangeProperty(updateItem.getCovidTest(),reqDto.getCovidTest())){
                String optionName = reqDto.getCovidTest() ? "필요" : "불필요";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"코로나 검사",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setCovidTest(reqDto.getCovidTest());
        }
        if(reqDto.getBenefitsVaccination() != null ){
            if(hasChangeProperty(updateItem.getBenefitsVaccination(),reqDto.getBenefitsVaccination())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"백신 접종자 혜택",
                        reqDto.getBenefitsVaccination(),updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setBenefitsVaccination(reqDto.getBenefitsVaccination());
        }
        if(reqDto.getPossibleExempted() != null){
            if(hasChangeProperty(updateItem.getPossibleExempted(),reqDto.getPossibleExempted())){
                String optionName = reqDto.getPossibleExempted() ? "가능" : "불가능";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"면제 가능 여부",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setPossibleExempted(reqDto.getPossibleExempted());
        }

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updateItem,ImmigrationStatusDetailResDto.class);
    }
    private boolean hasChangeProperty(Object tableProperty,Object reqDtoProperty){
        if((tableProperty != null && !tableProperty.equals(reqDtoProperty)) ||
                (tableProperty == null && reqDtoProperty != null)){
            return true;
        }else {
            return false;
        }
    }

    private void sendAlarmMessage(String nationName,String filterName,String optionName
            ,int nationId, List<UserBookMarkingCountry> alarmUsers){
        String message = nationName + "의 " + filterName+"이 " + optionName+"으로 변경 되었습니다.";

        List<UserAlarm> saveItems = alarmUsers.stream().map(x -> {
            FcmMessageReqDto fcmMessageReqDto = new FcmMessageReqDto();
            fcmMessageReqDto.setMessage(message);
            fcmMessageReqDto.setLinkUrl("https://wecango.org/BM004/"+nationId);
            fcmMessageReqDto.setTitle("wecango");
            fcmMessageReqDto.setType("nation");
            fcmMessageReqDto.setUid(x.getUserUid().getUid());
            try {
                fireBaseService.sendMessage(fcmMessageReqDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            UserAlarm alarm = UserAlarm.builder()
                    .message(message)
                    .sendDateTime(LocalDateTime.now())
                    .relationNationId(x.getNationId())
                    .readFlag(false)
                    .userUid(x.getUserUid())
                    .build();
            return alarm;
        }).collect(Collectors.toList());
        userAlarmDataRepository.saveAll(saveItems);
    }
}
