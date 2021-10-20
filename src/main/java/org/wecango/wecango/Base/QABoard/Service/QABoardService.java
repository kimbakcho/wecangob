package org.wecango.wecango.Base.QABoard.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Dto.QABoardComResDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardFilterReqDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardInsertDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;
import org.wecango.wecango.Base.QABoard.Repository.QABoardDataRepository;
import org.wecango.wecango.Base.QABoard.Repository.QABoardQueryRepository;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyResDto;
import org.wecango.wecango.Base.QABoardReply.Repository.QABoardReplyDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QABoardService {

    final QABoardDataRepository qaBoardDataRepository;
    final NationControlDataRepository nationControlDataRepository;
    final QABoardQueryRepository qaBoardQueryRepository;
    final QABoardReplyDataRepository qaBoardReplyDataRepository;

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

    public void viewCount(Integer id) {
        QABoard byId = qaBoardDataRepository.getById(id);
        byId.setView(byId.getView()+1);
    }

    public Page<QABoardComResDto> getFilterDoc(QABoardFilterReqDto reqDto, Pageable pageable) {
        Page<QABoard> filterDoc = qaBoardQueryRepository.getFilterDoc(reqDto, pageable);
        ModelMapper modelMapper = new ModelMapper();
        Page<QABoardComResDto> resDtos = filterDoc.map(x -> {
            QABoardComResDto boardResDto = modelMapper.map(x, QABoardComResDto.class);
            if(reqDto.getWithComment()){
                QABoard qaBoard = qaBoardDataRepository.getById(x.getId());
                System.out.println(qaBoard.getId());
                List<QABoardReply> replyList = qaBoardReplyDataRepository
                        .findFirstByQaBoardIdOrderByRepresentativeCommentDescUpdateDateTimeAsc(qaBoard);
                if(replyList.size()> 0){
                    QABoardReply qaBoardReply = replyList.get(0);
                    boardResDto.setQaBoardReplyResDto(modelMapper.map(qaBoardReply, QABoardReplyResDto.class));
                }
            }
            return boardResDto;
        });
        return resDtos;
    }
}
