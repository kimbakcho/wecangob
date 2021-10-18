package org.wecango.wecango.Base.QABoardReply.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;
import org.wecango.wecango.Base.QABoard.Dto.QABoardResDto;

@Data
public class QABoardReplyInsertReqDto {
    String nationName;
    String qaBoardCategory;
    Integer qaBoardId;
    String content;
}
