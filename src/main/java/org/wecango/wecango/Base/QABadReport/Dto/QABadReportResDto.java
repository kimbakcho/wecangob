package org.wecango.wecango.Base.QABadReport.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementResDto;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class QABadReportResDto {

    Integer idx;

    QABoardResDto qaDoc;

    MemberManagementSimpleResDto reportUser;

    LocalDateTime reportTime;

}
