package org.wecango.wecango.Base.QABoardReply.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;
import org.wecango.wecango.Base.QABoard.Domain.QABoard;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class QABoardReplyResDto {
    Integer id;
    NationControlResDto nationName;
    String qaBoardCategory;
    QABoardResDto qaBoardId;
    MemberManagementSimpleResDto writer;
    String content;
    LocalDateTime updateDateTime;
    Integer representativeComment;
}
