package org.wecango.wecango.Base.QABoard.Dto;

import lombok.Data;
import org.wecango.wecango.Base.Common.PageReqDto;

import java.util.List;

@Data
public class QABoardFilterReqDto {
    String mode;
    String title;
    String writer;
    String content;
    String nation;
    String category;
    PageReqDto pageReqDto;

}
