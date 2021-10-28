package org.wecango.wecango.Base.QABoard.Dto;

import lombok.Data;

@Data
public class QABoardUpdateDto {

    Integer id;

    String classificationQuestions;

    String nationName;


    String title;

    String contentText;

    String contentImageUrl;
}
