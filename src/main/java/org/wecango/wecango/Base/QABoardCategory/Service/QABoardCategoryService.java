package org.wecango.wecango.Base.QABoardCategory.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.wecango.wecango.Base.QABoardCategory.Domain.QABoardCategory;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryResDto;
import org.wecango.wecango.Base.QABoardCategory.Repository.QABoardCategoryDataRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QABoardCategoryService {

    final QABoardCategoryDataRepository qaBoardCategoryDataRepository;

    public List<QABoardCategoryResDto> getList(){
        List<QABoardCategory> byOrderByOrderIdxAsc = qaBoardCategoryDataRepository.findAllByOrderByOrderIdxAsc();

        ModelMapper modelMapper = new ModelMapper();
        List<QABoardCategoryResDto> collect = byOrderByOrderIdxAsc.stream().map(x -> {
            return modelMapper.map(x, QABoardCategoryResDto.class);
        }).collect(Collectors.toList());

        return collect;
    }
}
