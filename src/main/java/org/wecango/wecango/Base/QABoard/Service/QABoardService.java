package org.wecango.wecango.Base.QABoard.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Dto.QABoardInsertDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;
import org.wecango.wecango.Base.QABoard.Repository.QABoardDataRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class QABoardService {

    final QABoardDataRepository qaBoardDataRepository;
    final NationControlDataRepository nationControlDataRepository;

    public QABoardResDto insert(MemberManagement memberManagement, QABoardInsertDto qaBoardInsertDto){
        ModelMapper modelMapper = new ModelMapper();
        NationControl byNationName = nationControlDataRepository.getByNationName(qaBoardInsertDto.getNationName());
        QABoard build = QABoard.builder()
                .title(qaBoardInsertDto.getTitle())
                .classificationQuestions(qaBoardInsertDto.getClassificationQuestions())
                .contentImageUrl(qaBoardInsertDto.getContentImageUrl())
                .contentText(qaBoardInsertDto.getContentText())
                .nationName(byNationName)
                .replyCount(0)
                .representative(0)
                .view(0)
                .writer(memberManagement)
                .updateDateTime(LocalDateTime.now())
                .build();

        QABoard save = qaBoardDataRepository.save(build);
        return modelMapper.map(save,QABoardResDto.class);
    }

    public QABoardResDto getDoc(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        QABoard byId = qaBoardDataRepository.getById(id);
        return modelMapper.map(byId,QABoardResDto.class);
    }
}
