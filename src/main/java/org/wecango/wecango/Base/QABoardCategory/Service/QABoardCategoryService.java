package org.wecango.wecango.Base.QABoardCategory.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.QABoardCategory.Domain.QABoardCategory;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryNameChangeReqDto;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryOrderChangReqDto;
import org.wecango.wecango.Base.QABoardCategory.Dto.QABoardCategoryResDto;
import org.wecango.wecango.Base.QABoardCategory.Repository.QABoardCategoryDataRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class QABoardCategoryService {

    final QABoardCategoryDataRepository qaBoardCategoryDataRepository;

    public List<QABoardCategoryResDto> getList(){
        List<QABoardCategory> byOrderByOrderIdxAsc = qaBoardCategoryDataRepository
                .findAllByOrderByOrderIdxAsc();

        ModelMapper modelMapper = new ModelMapper();
        List<QABoardCategoryResDto> collect = byOrderByOrderIdxAsc.stream().map(x -> {
            return modelMapper.map(x, QABoardCategoryResDto.class);
        }).collect(Collectors.toList());

        return collect;
    }

    public QABoardCategoryResDto changeName(QABoardCategoryNameChangeReqDto reqDto) {
        QABoardCategory changeItem = qaBoardCategoryDataRepository.getByCategoryName(reqDto.getOldCategoryName());
        changeItem.setCategoryName(reqDto.getNewCategoryName());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(changeItem,QABoardCategoryResDto.class);
    }


    public void changeOrder(QABoardCategoryOrderChangReqDto reqDto) {
        QABoardCategory replace1 = qaBoardCategoryDataRepository.getByCategoryName(reqDto.getReplaceCategoryName1());
        QABoardCategory replace2 = qaBoardCategoryDataRepository.getByCategoryName(reqDto.getReplaceCategoryName2());
        Integer replace1OrderIdx = replace1.getOrderIdx();
        Integer replace2OrderIdx = replace2.getOrderIdx();
        replace2.setOrderIdx(-1);
        qaBoardCategoryDataRepository.flush();
        replace1.setOrderIdx(replace2OrderIdx);
        qaBoardCategoryDataRepository.flush();
        replace2.setOrderIdx(replace1OrderIdx);
    }

    public void deleteItem(String categoryName) {
        qaBoardCategoryDataRepository.deleteByCategoryName(categoryName);
    }

    public QABoardCategoryResDto inputName(String name) {
        List<QABoardCategory> byOrderByOrderIdxAsc = qaBoardCategoryDataRepository
                .findAllByOrderByOrderIdxAsc();

        QABoardCategory qaBoardCategory = byOrderByOrderIdxAsc.get(byOrderByOrderIdxAsc.size() - 1);

        QABoardCategory saveItem = QABoardCategory.builder()
                .categoryName(name)
                .orderIdx(qaBoardCategory.getOrderIdx() + 1 )
                .build();
        QABoardCategory save = qaBoardCategoryDataRepository.save(saveItem);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(save,QABoardCategoryResDto.class);
    }
}
