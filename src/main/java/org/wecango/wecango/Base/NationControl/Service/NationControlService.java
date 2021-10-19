package org.wecango.wecango.Base.NationControl.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlReqDto;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.NationControl.Repository.NationControlQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NationControlService {

    final NationControlQueryRepository nationControlQueryRepository;

    final NationControlDataRepository nationControlDataRepository;

    public List<NationControlResDto> getFilter(NationControlReqDto reqDto){
        return nationControlQueryRepository.getFilter(reqDto);
    }

    public void setDisplayFlag(Integer id, Boolean flag) {
        NationControl byId = nationControlDataRepository.getById(id);
        byId.setDisplayFlag(flag);
    }
}
