package org.wecango.wecango.Base.QABoard.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QABoardInsertDto {
    String classificationQuestions;

    String nationName;


    String title;

    String contentText;

    String contentImageUrl;
}
