package org.wecango.wecango.Base.QABoard.Dto;

import lombok.Data;
import org.wecango.wecango.Base.QABoardReply.Dto.QABoardReplyResDto;

@Data
public class QABoardComResDto extends QABoardResDto{
    QABoardReplyResDto qaBoardReplyResDto;
}
