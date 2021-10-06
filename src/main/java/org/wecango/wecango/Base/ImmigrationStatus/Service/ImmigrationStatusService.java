package org.wecango.wecango.Base.ImmigrationStatus.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.ImmigrationStatus.Domain.ImmigrationStatus;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusDetailResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Dto.ImmigrationStatusSimpleResDto;
import org.wecango.wecango.Base.ImmigrationStatus.Repository.ImmigrationStatusDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImmigrationStatusService {
    final ImmigrationStatusDataRepository immigrationStatusDataRepository;
    final NationControlDataRepository nationControlDataRepository;

    public ImmigrationStatusDetailResDto getNationInfo(Integer id){
        NationControl nationControl = nationControlDataRepository.getById(id);
        ImmigrationStatus byId = immigrationStatusDataRepository.getByNationId(nationControl);
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
}
