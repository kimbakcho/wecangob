package org.wecango.wecango.Base.ImmigrationStatus.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationInfoManagement;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusDetailResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusUpdateReqDto;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationInfoManagementDataRepository;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationStatusDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;

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


        if(reqDto.getNationFlagImageUrl() != null ){
            updateItem.getNationId().setFlagImage(reqDto.getNationFlagImageUrl() );
            updateItem.getNationId().setFileName(reqDto.getNationFlagImageFileName());
        }
        if(reqDto.getContinent() != null){
            updateItem.setContinent(reqDto.getContinent());
        }
        if(reqDto.getTravelFlag()!=null){
            updateItem.setTravelFlag(reqDto.getTravelFlag());
        }
        if(reqDto.getMandatoryQuarantine() != null){
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
            updateItem.setVaccinationFlag(reqDto.getVaccinationFlag());
        }
        if(reqDto.getPcrTest() != null){
            updateItem.setPcrTest(reqDto.getPcrTest());
        }
        if(reqDto.getMandatoryQuarantineText() != null){
            updateItem.setMandatoryQuarantineText(reqDto.getMandatoryQuarantineText());
        }
        if(reqDto.getVisaFlag() != null){
            updateItem.setVisaFlag(reqDto.getVisaFlag());
        }
        if(reqDto.getCovidTest() != null){
            updateItem.setVisaFlag(reqDto.getCovidTest());
        }
        if(reqDto.getBenefitsVaccination() != null){
            updateItem.setBenefitsVaccination(reqDto.getBenefitsVaccination());
        }
        if(reqDto.getPossibleExempted() != null){
            updateItem.setPossibleExempted(reqDto.getPossibleExempted());
        }

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updateItem,ImmigrationStatusDetailResDto.class);
    }
}
