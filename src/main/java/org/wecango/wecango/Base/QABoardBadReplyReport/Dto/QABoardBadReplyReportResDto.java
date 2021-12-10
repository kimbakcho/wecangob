package org.wecango.wecango.Base.QABoardBadReplyReport.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.QABoardReply.Domain.QABoardReply;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyResDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class QABoardBadReplyReportResDto {
    Integer idx;

    QABoardReplyResDto reply;

    MemberManagementSimpleResDto reportUser;

    LocalDateTime reportTime;
}
