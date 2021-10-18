package org.wecango.wecango.Base.QABoard.Dto;

import lombok.Data;
import org.wecango.wecango.Base.MemberManagement.Dto.MemberManagementSimpleResDto;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;
import org.wecango.wecango.Base.NationControl.Dto.NationControlResDto;

import java.time.LocalDateTime;

@Data
public class QABoardResDto {

    Integer id;

    String classificationQuestions;

    NationControlResDto nationName;

    MemberManagementSimpleResDto writer;

    String title;

    String contentText;

    String contentImageUrl;

    Integer replyCount;

    Integer view;

    LocalDateTime updateDateTime;

    Integer representative;

}
