package org.wecango.wecango.Base.QABoardBadReplyReport.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.QABoardBadReplyReport.Domain.QABoardBadReplyReport;
import org.wecango.wecango.Base.QABoardBadReplyReport.Dto.QABoardBadReplyReportResDto;
import org.wecango.wecango.Base.QABoardBadReplyReport.Repository.QABoardBadReplyReportDataRepository;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;
import org.wecango.wecango.Base.QABoardReply.Repository.QABoardReplyDataRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class QABoardBadReplyReportService {

    final QABoardBadReplyReportDataRepository qaBoardBadReplyReportDataRepository;

    final QABoardReplyDataRepository qaBoardReplyDataRepository;

    public QABoardBadReplyReportResDto report(Integer id, MemberManagement reportUser) {
        QABoardReply qaBoardReply = qaBoardReplyDataRepository.getById(id);
        QABoardBadReplyReport qaBoardBadReplyReport = QABoardBadReplyReport.builder()
                .reply(qaBoardReply)
                .reportUser(reportUser)
                .reportTime(LocalDateTime.now())
                .build();

        QABoardBadReplyReport save = qaBoardBadReplyReportDataRepository.save(qaBoardBadReplyReport);

        qaBoardReply.setBadReportCount(qaBoardReply.getBadReportCount()+1);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(save,QABoardBadReplyReportResDto.class);
    }

    public Page<QABoardBadReplyReportResDto> getReports(PageRequest pageable) {
        Page<QABoardBadReplyReport> reports = qaBoardBadReplyReportDataRepository.findAll(pageable);
        ModelMapper modelMapper = new ModelMapper();
        Page<QABoardBadReplyReportResDto> boardBadReplyReportResDtos = reports.map(x -> {
            return modelMapper.map(x, QABoardBadReplyReportResDto.class);
        });
        return boardBadReplyReportResDtos;
    }

    public void deleteReportReply(Integer id) {
        QABoardBadReplyReport badReplyReport = qaBoardBadReplyReportDataRepository.getById(id);
        qaBoardReplyDataRepository.delete(badReplyReport.getReply());
    }
}
