package org.wecango.wecango.Base.QABoardReply.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Repository.NationControlDataRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Repository.QABoardDataRepository;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyInsertReqDto;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyResDto;
import org.wecango.wecango.Base.QABoardReply.Repository.QABoardReplyDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QABoardReplyService {

    final QABoardReplyDataRepository qaBoardReplyDataRepository;

    final QABoardDataRepository qaBoardDataRepository;

    final MemberManagementDataRepository memberManagementDataRepository;

    final NationControlDataRepository nationControlDataRepository;


    public QABoardReplyResDto insert(MemberManagement memberManagement, QABoardReplyInsertReqDto reqDto) {

        QABoard qaBoard = qaBoardDataRepository.getById(reqDto.getQaBoardId());

        NationControl nationName = nationControlDataRepository.getByNationName(reqDto.getNationName());

        QABoardReply qaBoardReply = QABoardReply.builder()
                .content(reqDto.getContent())
                .representativeComment(0)
                .updateDateTime(LocalDateTime.now())
                .qaBoardId(qaBoard)
                .writer(memberManagement)
                .qaBoardCategory(reqDto.getQaBoardCategory())
                .nationName(nationName)
                .build();

        QABoardReply save = qaBoardReplyDataRepository.save(qaBoardReply);

        qaBoard.setReplyCount(qaBoard.getReplyCount() + 1);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(save,QABoardReplyResDto.class);
    }

    public List<QABoardReplyResDto> getQaDocReplys(Integer qaDocId) {
        QABoard qaBoard = qaBoardDataRepository.getById(qaDocId);
        List<QABoardReply> replyList = qaBoardReplyDataRepository.findByQaBoardIdOrderByRepresentativeCommentDescUpdateDateTimeAsc(qaBoard);
        ModelMapper modelMapper = new ModelMapper();
        List<QABoardReplyResDto> collect = replyList.stream().map(x -> {
            return modelMapper.map(x, QABoardReplyResDto.class);
        }).collect(Collectors.toList());
        return collect;
    }


    public void changeRepresentativeComment(Integer replyNumber, Integer changeOrder) {
        QABoardReply byId = qaBoardReplyDataRepository.getById(replyNumber);
        byId.setRepresentativeComment(changeOrder);
    }
}
