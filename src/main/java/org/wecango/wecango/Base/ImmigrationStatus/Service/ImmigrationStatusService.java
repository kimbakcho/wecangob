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
    final NationChangeAlarmDataRepository nationChangeAlarmDataRepository;
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
            ImmigrationInfoManagement vaccinatedLeavesCountry = ImmigrationInfoManagement.builder()
                    .classification("VaccinatedReturnHome")
                    .nationId(nationControl)
                    .contentHtml("")
                    .contentMarkDown("")
                    .updateDateTime(LocalDateTime.now())
                    .build();
            ImmigrationInfoManagement save = immigrationInfoManagementDataRepository.save(vaccinatedLeavesCountry);
            byId.setVaccinatedReturnHome(save);
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
            ImmigrationInfoManagement vaccinatedLeavesCountry = ImmigrationInfoManagement.builder()
                    .classification("UnvaccinatedReturnHome")
                    .nationId(nationControl)
                    .contentHtml("")
                    .contentMarkDown("")
                    .updateDateTime(LocalDateTime.now())
                    .build();
            ImmigrationInfoManagement save = immigrationInfoManagementDataRepository.save(vaccinatedLeavesCountry);
            byId.setUnvaccinatedReturnHome(save);
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

        List<NationChangeAlarm> alarmUsers = nationChangeAlarmDataRepository.findByNationId(updateItem.getNationId());

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
            if(updateItem.getTravelFlag() != reqDto.getTravelFlag()){
                String optionName = reqDto.getTravelFlag() ? "가능" : "불가능";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"여행 가능 여부",
                        optionName,updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setTravelFlag(reqDto.getTravelFlag());
        }
        if(reqDto.getMandatoryQuarantine() != null){
            if(updateItem.getMandatoryQuarantine() != null && !updateItem.getMandatoryQuarantine().equals(reqDto.getMandatoryQuarantine())){
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
            if(updateItem.getVaccinationFlag()!=null && !updateItem.getVaccinationFlag().equals(reqDto.getVaccinationFlag()) ){
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
            if( updateItem.getPcrTest() != null && !updateItem.getPcrTest().equals(reqDto.getPcrTest()) ){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"PCR 검사",
                        reqDto.getPcrTest(),updateItem.getNationId().getId(),alarmUsers);
            }
            updateItem.setPcrTest(reqDto.getPcrTest());

        }
        if(reqDto.getMandatoryQuarantineText() != null){
            if(updateItem.getMandatoryQuarantineText() != null &&
                    !updateItem.getMandatoryQuarantineText().equals(reqDto.getMandatoryQuarantineText())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"의무 격리",
                        reqDto.getMandatoryQuarantineText(),updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setMandatoryQuarantineText(reqDto.getMandatoryQuarantineText());

        }
        if(reqDto.getVisaFlag() != null ){
            if(updateItem.getVisaFlag() != reqDto.getVisaFlag()){
                String optionName = reqDto.getVisaFlag() ? "필요" : "불필요";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"Visa 필요 유무",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setVisaFlag(reqDto.getVisaFlag());
        }
        if(reqDto.getCovidTest() != null ){
            if(updateItem.getCovidTest() != reqDto.getCovidTest()){
                String optionName = reqDto.getCovidTest() ? "필요" : "불필요";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"코로나 검사",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setCovidTest(reqDto.getCovidTest());
        }
        if(reqDto.getBenefitsVaccination() != null ){
            if(updateItem.getBenefitsVaccination() != null &&
                    updateItem.getBenefitsVaccination().equals(reqDto.getBenefitsVaccination())){
                sendAlarmMessage(updateItem.getNationId().getNationName(),"백신 접종자 혜택",
                        reqDto.getBenefitsVaccination(),updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setBenefitsVaccination(reqDto.getBenefitsVaccination());
        }
        if(reqDto.getPossibleExempted() != null){
            if(updateItem.getPossibleExempted() != reqDto.getPossibleExempted()){
                String optionName = reqDto.getPossibleExempted() ? "가능" : "불가능";
                sendAlarmMessage(updateItem.getNationId().getNationName(),"면제 가능 여부",
                        optionName,updateItem.getNationId().getId() ,alarmUsers);
            }
            updateItem.setPossibleExempted(reqDto.getPossibleExempted());
        }

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updateItem,ImmigrationStatusDetailResDto.class);
    }

    private void sendAlarmMessage(String nationName,String filterName,String optionName
            ,int nationId, List<NationChangeAlarm> alarmUsers){
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
            } catch (FirebaseMessagingException e) {
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
