package org.wecango.wecango.Base.QABadReport.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Repository.MemberManagementDataRepository;
import org.wecango.wecango.Base.QABadReport.Domain.QABadReport;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportReqDto;
import org.wecango.wecango.Base.QABadReport.Dto.QABadReportResDto;
import org.wecango.wecango.Base.QABadReport.Repository.QABadReportDataRepository;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Repository.QABoardDataRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class QABadReportService {

    final QABadReportDataRepository qaBadReportDataRepository;

    final QABoardDataRepository qaBoardDataRepository;

    final MemberManagementDataRepository memberManagementDataRepository;

    public QABadReportResDto save(QABadReportReqDto reqDto, MemberManagement reportUser){

        QABoard qaBoard = qaBoardDataRepository.getById(reqDto.getQaDodId());

        QABadReport qaBadReport = QABadReport.builder()
                .qaDoc(qaBoard)
                .reportUser(reportUser)
                .reportTime(LocalDateTime.now())
                .build();

        QABadReport save = qaBadReportDataRepository.save(qaBadReport);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(save, QABadReportResDto.class);
    }
}
